package cn.m1yellow.mypages.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    /**
     * 获取文件路径
     *
     * @param clazz        Class
     * @param profile      文件
     * @param originalPath 原文件路径
     * @param isNewName    是否使用新名称
     * @param isFullPath   是否取全路径
     * @return
     */
    public static String getFilePath(Class clazz, MultipartFile profile, String saveDir, String originalPath, boolean isNewName, boolean isFullPath) {

        if (null == profile && StringUtils.isBlank(originalPath)) {
            return null;
        }

        saveDir = clazz.getResource("/").getPath() + saveDir;
        //System.out.println((">>>> saveDir: " + saveDir));

        String fileName = null;
        try {
            if (null != profile) { // 优先使用文件获取路径
                fileName = profile.getOriginalFilename();
            } else {
                fileName = originalPath.substring(originalPath.lastIndexOf("/") + 1);
            }
            // 重新生成文件名
            if (isNewName) {
                // 获取后缀 .jpg
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                fileName = UUIDGenerateUtil.getUUID40() + suffixName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (StringUtils.isBlank(fileName)) {
                return null;
            }
        }

        // 文件全路径
        String filePath = saveDir + fileName;

        if (isFullPath) {
            return filePath;
        }

        // 相对路径
        String profileDir = filePath.substring(filePath.lastIndexOf("/images"));

        return profileDir;
    }


    /**
     * 获取文件保存位置全路径（classpath 目录下）
     *
     * @param clazz
     * @param saveDir
     * @return
     */
    public static String getSaveDirFullPath(Class clazz, String saveDir) {
        if (StringUtils.isBlank(saveDir)) {
            return null;
        }

        saveDir = clazz.getResource("/").getPath() + saveDir;

        return saveDir;
    }

}
