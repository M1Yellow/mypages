package cn.m1yellow.mypages.excavation.service.impl;

import cn.m1yellow.mypages.common.service.FileDownloadService;
import cn.m1yellow.mypages.common.service.OssService;
import cn.m1yellow.mypages.common.util.HeaderUtil;
import cn.m1yellow.mypages.common.util.HttpClientUtil;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.excavation.bo.UserInfoItem;
import cn.m1yellow.mypages.excavation.service.DataExcavateService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * B站图片获取
 */
@Slf4j
@Service("dataOfBiliExcavateService")
public class DataOfBiliExcavateServiceImpl implements DataExcavateService {

    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.dir.avatar}")
    private String ALIYUN_OSS_DIR_AVATAR;

    // TODO 这里报错，实际是能通过编译的
    @Resource(name = "httpClientDownloadService")
    FileDownloadService httpClientDownloadService;
    @Resource
    private OssService ossService;


    /**
     * 从网页元素中获取图片地址下载（暂未使用）
     *
     * @param fromUrl
     * @param saveDir
     * @param params
     * @return
     */
    @Override
    public UserInfoItem singleImageDownloadFromHtml(String fromUrl, String saveDir, Map<String, Object> params) {
        // 获取 html 对象
        String html = HttpClientUtil.getHtml(fromUrl, HeaderUtil.getOneHeaderRandom());
        Document doc = Jsoup.parse(html, "UTF-8");

        // 指定获取信息的元素位置
        UserInfoItem infoItem = new UserInfoItem();
        // 用户名
        String userName = doc.getElementById("h-name").text();
        infoItem.setUserName(userName);
        // 个性签名
        String signature = doc.getElementsByClass("h-sign").first().text();
        infoItem.setSignature(signature);
        // 头像地址
        String imgUrl = doc.getElementById("h-avatar").attr("src");
        if (imgUrl.indexOf("@") > -1) { // 截取去掉后面的 webp 参数
            imgUrl = imgUrl.substring(0, imgUrl.indexOf("@"));
        }
        // 头像名称（包含文件类型）
        //String headImgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
        String[] imgUrlArr = imgUrl.split("/");
        String headImgName = imgUrlArr[imgUrlArr.length - 1];
        infoItem.setHeadImgName(headImgName);

        // 下载文件
        if (params == null) params = new HashMap<>();
        params.put("userAgent", HeaderUtil.getOneHeaderRandom());
        params.put("referer", fromUrl);
        httpClientDownloadService.singleFileDownload(imgUrl, headImgName, saveDir, params);

        // 保存信息入库，在 admin 模块操作

        infoItem.setHeadImgPath(saveDir);

        return infoItem;
    }

    @Override
    public UserInfoItem singleImageDownloadFromJson(String fromUrl, String saveDir, Map<String, Object> params) {

        String result = HttpClientUtil.getHtml(fromUrl, HeaderUtil.getOneHeaderRandom());
        JSONObject resultObject = JSON.parseObject(result);
        JSONObject dataObject = JSON.parseObject(resultObject.getString("data"));

        // 指定获取信息的元素位置
        UserInfoItem infoItem = new UserInfoItem();
        // 用户名
        String userName = dataObject.getString("name");
        infoItem.setUserName(userName);
        // 个性签名
        String signature = dataObject.getString("sign");
        infoItem.setSignature(signature);
        // 头像地址
        String imgUrl = dataObject.getString("face");
        if (imgUrl.indexOf("@") > -1) { // 截取去掉后面的 webp 参数
            imgUrl = imgUrl.substring(0, imgUrl.indexOf("@"));
        }
        infoItem.setHeadImgUrl(imgUrl);

        // 头像名称（包含文件类型）
        //String headImgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
        String[] imgUrlArr = imgUrl.split("/");
        String headImgName = imgUrlArr[imgUrlArr.length - 1];
        infoItem.setHeadImgName(headImgName);

        // 下载文件
        if (params == null) params = new HashMap<>();
        params.put("userAgent", HeaderUtil.getOneHeaderRandom());
        params.put("referer", fromUrl);
        // TODO 下载之前，校验当前文件名是否跟原来地一致，不一致需要删除原来的文件，再下载新文件，避免文件堆积
        String profileOriginalDir = ObjectUtil.getString(params.get("profileOriginalDir"));
        //String filePath = saveDir.substring(saveDir.lastIndexOf("/images")) + headImgName;
        //String filePath = "/" + ALIYUN_OSS_DIR_AVATAR + headImgName;
        String filePath = ossService.getPathV2(ALIYUN_OSS_DIR_AVATAR, headImgName);

        // 上传到OSS
        InputStream is = null;
        try {
            is = new URL(imgUrl).openStream();
            boolean saveResult = ossService.saveFile(ALIYUN_OSS_BUCKET_NAME, is, profileOriginalDir, filePath);
            if (!saveResult) {
                log.info(">>>> OSS 文件保存失败：{}", filePath);
                return null;
            }
        } catch (Exception e) {
            log.info(">>>> OSS 文件保存异常: ", e);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("OSS 文件流关闭异常", e);
                }
            }
        }

        /*
        // 保存到服务器本机
        if (StringUtils.isNotBlank(profileOriginalDir)) {
            // 原来的文件名
            String headImgOriginalName = profileOriginalDir.substring(profileOriginalDir.lastIndexOf("/") + 1);
            if (!headImgName.equals(headImgOriginalName)) {
                // 新文件名跟原来的文件名不同，删除原来的文件
                String filePath = saveDir + headImgOriginalName;
                File oldFile = new File(filePath);
                if (oldFile.exists() && oldFile.isFile()) {
                    if (oldFile.delete()) {
                        log.info(filePath + " 删除成功。");
                    } else {
                        log.info(filePath + " 删除失败。");
                        return infoItem;
                    }
                }
            } else {
                // TODO 文件名相同，需要进一步校验 MD5/HASH，判断是否为统一文件，不是同一文件再进行下载。后续优化
                // 获取原来文件的 MD5
                String originalFilePath = saveDir + headImgOriginalName;
                String originalFileMd5 = null;
                try (
                        FileInputStream fis = new FileInputStream(new File(originalFilePath))
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
            }
        }

        // 下载
        httpClientDownloadService.singleFileDownload(imgUrl, headImgName, saveDir, params);
        */

        infoItem.setHeadImgPath(filePath);

        return infoItem;
    }
}
