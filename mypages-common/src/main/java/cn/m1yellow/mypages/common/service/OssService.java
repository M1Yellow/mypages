package cn.m1yellow.mypages.common.service;

import cn.hutool.core.date.DateUtil;
import cn.m1yellow.mypages.common.dto.OssCallbackResult;
import cn.m1yellow.mypages.common.dto.OssPolicyResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Oss 对象存储 Service
 */
public interface OssService {

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    public default String getPath(String prefix, String suffix) {
        // 生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 文件路径
        String path = DateUtil.format(new Date(), "yyyyMMdd") + "/" + uuid;

        if (StringUtils.isNotBlank(prefix)) {
            if (prefix.lastIndexOf("/") != prefix.length() - 1) {
                prefix = prefix + "/";
            }
            path = prefix + path;
        }

        return path + suffix;
    }

    /**
     * 文件路径
     *
     * @param prefix   前缀
     * @param fileName 文件名，包含后缀
     * @return 返回上传路径
     */
    public default String getPathV2(String prefix, String fileName) {
        // 文件路径
        String path = fileName;

        if (StringUtils.isNotBlank(prefix)) {
            // 路径开头加上"/"
            if (!prefix.startsWith("/")) {
                prefix = "/" + prefix;
            }
            // 路径最后加上"/"
            if (prefix.lastIndexOf("/") != prefix.length() - 1) {
                prefix = prefix + "/";
            }
            // OSS 保存位置需要去掉 public/
            prefix = prefix.replace("public/", "");
            path = prefix + path;
        }

        return path;
    }

    /**
     * 获取 OSS 图片服务器地址
     *
     * @return
     */
    String getOssHost();

    /**
     * Oss上传策略生成
     */
    OssPolicyResult policy();

    /**
     * Oss上传成功回调
     */
    OssCallbackResult callback(HttpServletRequest request);

    /**
     * 文件流形式上传
     *
     * @param bucketName OSS 创建的 bucket 名称
     * @param filePath   文件路径，包含文件名，不包含 OSS 服务器地址和 OSS bucket 名称
     * @param is         文件输入流
     */
    boolean upload(String bucketName, String filePath, InputStream is);

    /**
     * 文件上传
     *
     * @param bucketName OSS 创建的 bucket 名称
     * @param is         字节流
     * @param filePath   文件路径，包含文件名
     * @param isFullPath 是否返回全路径（包含域名）
     * @return 返回http地址
     */
    String upload(String bucketName, InputStream is, String filePath, boolean isFullPath);

    /**
     * 文件形式上传
     *
     * @param bucketName OSS 创建的 bucket 名称
     * @param filePath   文件路径，包含文件名，不包含 OSS 服务器地址和 OSS bucket 名称
     * @param file       文件对象
     */
    boolean upload(String bucketName, String filePath, File file);

    /**
     * 删除文件
     *
     * @param bucketName OSS bucket 名称
     * @param objectName 文件名称
     */
    boolean delete(String bucketName, String objectName);

    /**
     * 保存文件，先删除已存在的旧文件
     *
     * @param bucketName  OSS 创建的 bucket 名称
     * @param is          文件流
     * @param oldFilePath 旧文件路径，OSS 路径开头去掉 "/"
     * @param newFilePath 新文件路径，OSS 路径开头去掉 "/"
     * @return 操作结果
     */
    boolean saveFile(String bucketName, InputStream is, String oldFilePath, String newFilePath);

    /**
     * 在下载新文件之前，处理已存在的文件
     *
     * @param oldFilePath 旧文件路径，不包含域名的文件路径
     * @param newFileName 新文件名，有后缀，不包含路径
     * @param params      补充请求参数
     * @return 操作结果
     */
    boolean dealExistedFile(String oldFilePath, String newFileName, Map<String, Object> params);

    /**
     * 手动清理闲置连接方法
     * @param method 方法名称
     */
    void idleConnHandler(String method);

}
