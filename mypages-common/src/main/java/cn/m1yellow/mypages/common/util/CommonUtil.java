package cn.m1yellow.mypages.common.util;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

    private static final String[] URL_TOP_PATHS = {".com", ".cn", ".net", ".org"};


    /**
     * 从平台用户主页 url 中获取平台英文名称。各个网站的域名千奇百怪，很难做到匹配百分百的网站
     * 复杂例子：https://api.p1.weibo.com.cn/u/234927394247?from=place.bilibili.com/3453535676/video
     *
     * @param url
     * @return
     */
    public static String getPlatformNameEnFromUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }


        return null;
    }


    /**
     * 获取域名地址
     * 举例：
     * https://api.p1.weibo.com.cn/u/234927394247?from=place.bilibili.com/3453535676/video
     * https://api.p1.weibo.com.cn
     *
     * @param url
     * @return
     */
    public static String getSimpleUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        int lastIdx = 0; // 用于判断最终的结束位置
        for (String path : URL_TOP_PATHS) {
            if (url.contains(path)) {
                int idx = url.indexOf(path) + path.length();
                if (idx > lastIdx) {
                    lastIdx = idx;
                }
            }
        }

        return url.substring(0, lastIdx);
    }

}
