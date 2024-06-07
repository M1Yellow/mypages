package cn.m1yellow.mypages.common.service.impl;

import cn.m1yellow.mypages.common.dto.OssCallbackParam;
import cn.m1yellow.mypages.common.dto.OssCallbackResult;
import cn.m1yellow.mypages.common.dto.OssPolicyResult;
import cn.m1yellow.mypages.common.exception.FileSaveException;
import cn.m1yellow.mypages.common.service.OssService;
import cn.m1yellow.mypages.common.util.JSONUtil;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Oss对象存储管理Service实现类
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "aliyun.oss.enable", havingValue = "1") // 如果缺少 aliyun.oss.enable 配置项，则不会初始化这个类实例
public class OssServiceImpl implements OssService {

    @Value("${aliyun.oss.policy.expire}")
    private int ALIYUN_OSS_EXPIRE;
    @Value("${aliyun.oss.maxSize}")
    private int ALIYUN_OSS_MAX_SIZE;
    @Value("${aliyun.oss.callback}")
    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;

    @Autowired
    private OSS ossClient;
    @Autowired
    private PoolingHttpClientConnectionManager defaultConnManager;
    @Autowired
    private ClientBuilderConfiguration clientBuilderConfiguration;


    @Override
    public String getOssHost() {
        // https://mypages.oss-cn-shenzhen.aliyuncs.com/
        return "https://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/");
    }

    /**
     * 签名生成
     */
    @Override
    public OssPolicyResult policy() {
        OssPolicyResult result = new OssPolicyResult();
        // 存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = ALIYUN_OSS_DIR_PREFIX + sdf.format(new Date());
        // 签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);
        // 文件大小
        long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
        // 回调
        OssCallbackParam callback = new OssCallbackParam();
        callback.setCallbackUrl(ALIYUN_OSS_CALLBACK);
        callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        callback.setCallbackBodyType("application/x-www-form-urlencoded");
        // 提交节点
        String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;
        try {
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = ossClient.calculatePostSignature(postPolicy);
            String callbackData = BinaryUtil.toBase64String(JSONUtil.toJSON(callback).getBytes("utf-8"));
            // 返回结果
            //result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            result.setPolicy(policy);
            result.setSignature(signature);
            result.setDir(dir);
            result.setCallback(callbackData);
            result.setHost(action);
        } catch (Exception e) {
            log.error("签名生成失败：{}", e.getMessage());
        } finally {
            // TODO 项目全局共用一个 ossClient，不用关闭，关闭会报错
            /*
            if (null != ossClient) {
                ossClient.shutdown();
            }
            */
            // 执行手动清理闲置连接方法
            idleConnHandler("policy");
        }

        return result;
    }

    @Override
    public OssCallbackResult callback(HttpServletRequest request) {
        OssCallbackResult result = new OssCallbackResult();
        String filename = request.getParameter("filename");
        filename = "http://".concat(ALIYUN_OSS_BUCKET_NAME).concat(".").concat(ALIYUN_OSS_ENDPOINT).concat("/").concat(filename);
        result.setFilename(filename);
        result.setSize(request.getParameter("size"));
        result.setMimeType(request.getParameter("mimeType"));
        result.setWidth(request.getParameter("width"));
        result.setHeight(request.getParameter("height"));
        return result;
    }

    @Override
    public boolean upload(String bucketName, String filePath, InputStream is) {
        try {
            // OSS 文件路径名开头没有"/"
            if (filePath.startsWith("/") && filePath.length() > 1) {
                filePath = filePath.substring(1);
            }
            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            PutObjectResult result = ossClient.putObject(bucketName, filePath, is);
            //return result.getResponse().isSuccessful(); // response 为 null，报空指针
            // 根据返回的 ETag 是否为空，判断上传是否成功
            return StringUtils.isNotBlank(result.getETag());
        } catch (Exception e) {
            log.error(">>>> OSS upload Exception: {}", e.getMessage());
            return false;
        } finally {
            /*
            if (null != ossClient) {
                ossClient.shutdown();
            }
            */
            // 执行手动清理闲置连接方法
            idleConnHandler("upload");
        }
    }

    @Override
    public String upload(String bucketName, InputStream is, String filePath, boolean isFullPath) {
        try {
            // OSS 文件路径名开头没有"/"
            if (filePath.startsWith("/") && filePath.length() > 1) {
                filePath = filePath.substring(1);
            }
            ossClient.putObject(bucketName, filePath, is);
        } catch (Exception e) {
            throw new FileSaveException("OSS 文件上传失败", e);
        } finally {
            /*
            if (null != ossClient) {
                ossClient.shutdown();
            }
            */
            // 执行手动清理闲置连接方法
            idleConnHandler("upload");
        }

        if (isFullPath) {
            return this.getOssHost() + filePath;
        }

        //return filePath;
        // 兼容原始版本
        return "/" + filePath;
    }

    @Override
    public boolean upload(String bucketName, String filePath, File file) {
        PutObjectResult result = null;
        try {
            // OSS 文件路径名开头没有"/"
            if (filePath.startsWith("/") && filePath.length() > 1) {
                filePath = filePath.substring(1);
            }

            // 创建PutObjectRequest对象。
            // 填写Bucket名称、Object完整路径和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
            // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, file);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            /*
            // 上传回调参数。注意，私有 bucket 不支持
            Callback callback = new Callback();
            callback.setCallbackUrl(ALIYUN_OSS_CALLBACK);
            //（可选）设置回调请求消息头中Host的值，即您的服务器配置Host的值。
            // callback.setCallbackHost("yourCallbackHost");
            // 设置发起回调时请求body的值。
            callback.setCallbackBody("{\\\"filename\\\":${object},\\\"mimeType\\\":${mimeType},\\\"size\\\":${size},\\\"height\\\":${imageInfo.height}\\\"width\\\":${imageInfo.width}}");
            // 设置发起回调请求的Content-Type。
            callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
            // 设置发起回调请求的自定义参数，由Key和Value组成，Key必须以x:开始。
            //callback.addCallbackVar("x:var1", "value1");
            putObjectRequest.setCallback(callback);
            */

            // 上传文件
            result = ossClient.putObject(putObjectRequest);

        } catch (Exception e) {
            log.error(">>>> OSS upload Exception: {}", e.getMessage());
        } finally {
            /*
            if (null != ossClient) {
                ossClient.shutdown();
            }
            */
            // 执行手动清理闲置连接方法
            idleConnHandler("upload");
        }

        return null != result && result.getResponse().isSuccessful();
    }

    @Override
    public boolean delete(String bucketName, String objectName) {
        boolean rt = false;
        try {
            // OSS 文件路径名开头没有"/"
            if (objectName.startsWith("/") && objectName.length() > 1) {
                objectName = objectName.substring(1);
            }
            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            // 注意，OSS 文件不存在，返回的也是成功的 VoidResult
            VoidResult result = ossClient.deleteObject(bucketName, objectName);
            rt = result.getResponse().isSuccessful();
        } catch (Exception e) {
            // 这是一个自定义异常，会根据是否有 FileSaveException 处理业务，不必纠结 delete 方法中为什么会是 FileSaveException
            throw new FileSaveException("OSS 旧文件删除失败", e);
        } finally {
            /*
            if (null != ossClient) {
                ossClient.shutdown();
            }
            */
            // 执行手动清理闲置连接方法
            idleConnHandler("upload");
        }

        return rt;
    }

    @Override
    public boolean saveFile(String bucketName, InputStream is, String oldFilePath, String newFilePath) {

        /**
         * 存在问题
         * 先删除后上传：删除之后，如果上传失败，导致原来的文件丢失
         * 先上传后删除：新旧文件路径名称相同，导致上传了又被删除，判断新旧文件是否相同
         */

        // 上传新文件
        boolean upResult = this.upload(bucketName, newFilePath, is);
        if (!upResult) { // 新文件上传失败
            return false;
        }
        log.info(">>>> OSS saveFile newFilePath: {}", newFilePath);

        // 删除旧文件
        if (StringUtils.isNotBlank(oldFilePath) && !newFilePath.equals(oldFilePath)) {
            log.info(">>>> OSS saveFile oldFilePath: {}", oldFilePath);
            boolean delResult = this.delete(bucketName, oldFilePath);
            if (delResult) { // 旧文件本来就不存在时，OSS 删除返回的结果也是 true，不影响
                log.debug(">>>> OSS saveFile 旧文件已删除：{}", oldFilePath);
            } else {
                log.error(">>>> OSS saveFile 旧文件删除失败：{}", oldFilePath);
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean dealExistedFile(String oldFilePath, String newFileName, Map<String, Object> params) {
        if (StringUtils.isNotBlank(oldFilePath)) {
            // 原来的文件名
            String headImgOriginalName = oldFilePath.substring(oldFilePath.lastIndexOf("/") + 1);
            if (!newFileName.equals(headImgOriginalName)) {
                // 新文件名跟原来的文件名不同，删除原来的文件
                /*
                String filePath = saveDir + headImgOriginalName;
                File oldFile = new File(filePath);
                if (oldFile.exists() && oldFile.isFile()) {
                    if (oldFile.delete()) {
                        log.info(filePath + " 删除成功。");
                    } else {
                        log.info(filePath + " 删除失败。");
                        return false;
                    }
                }
                */
                log.info(">>>> dealExistedFile oldFilePath: {}", oldFilePath);
                boolean delResult = this.delete(ALIYUN_OSS_BUCKET_NAME, oldFilePath);
                if (delResult) {
                    log.info(">>>> dealExistedFile 旧文件已删除：{}", oldFilePath);
                } else {
                    log.error(">>>> dealExistedFile 旧文件删除失败：{}", oldFilePath);
                    return false;
                }
            } else {
                // TODO 文件名相同，可以直接下载覆盖。比较文件 MD5 是否相同，反而耗费更多的性能
                /*
                // 获取原来文件的 MD5
                String originalFilePath = saveDir + headImgOriginalName;
                String originalFileMd5 = null;
                try (
                        FileInputStream fis = new FileInputStream(new File(originalFilePath));
                ) {
                    originalFileMd5 = DigestUtils.md5Hex(fis);
                    if (StringUtils.isNotBlank(originalFileMd5)) {
                        params.put("originalFileMd5", originalFileMd5);
                    }
                } catch (FileNotFoundException e) {
                    log.error(e.getMessage());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                */
            }
        }

        return true;
    }

    @Override
    public void idleConnHandler(String method) {
        if (defaultConnManager.getTotalStats().getMax() >= 0) {
            // 用于测试关闭 connection manager
            //return;
        }
        log.debug(">>>> {} 方法开始执行手动清理闲置连接...", method);
        // 关闭失效的连接，方法内有 debug 日志，不用再打印
        defaultConnManager.closeExpiredConnections();
        // 可选的，关闭指定时间内空闲（不活动的）连接
        defaultConnManager.closeIdleConnections(clientBuilderConfiguration.getIdleConnectionTime(), TimeUnit.MILLISECONDS);
        log.debug(">>>> {} 方法执行手动清理闲置连接结束", method);
    }

}
