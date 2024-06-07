package cn.m1yellow.mypages.constant;

import cn.m1yellow.mypages.common.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;

public enum PlatformInfo {

    MYPAGES(1, "我的", "mypages"),
    BILIBILI(2, "B站", "bilibili"),
    WEIBO(3, "微博", "weibo"),
    DOUBAN(4, "豆瓣", "douban"),
    ZHIHU(5, "知乎", "zhihu");

    private int id;
    private String name;
    private String nameEn;

    PlatformInfo(int id, String name, String nameEn) {
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
    }

    public static String getName(int id) {
        for (PlatformInfo p : PlatformInfo.values()) {
            if (p.getId() == id) {
                return p.name;
            }
        }
        return null;
    }

    public static String getNameEn(int id) {
        for (PlatformInfo p : PlatformInfo.values()) {
            if (p.getId() == id) {
                return p.nameEn;
            }
        }
        return null;
    }

    public static PlatformInfo getPlatformInfo(int id) {
        for (PlatformInfo p : PlatformInfo.values()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public static PlatformInfo getPlatformInfo(String nameEn) {
        for (PlatformInfo p : PlatformInfo.values()) {
            if (p.getNameEn().equals(nameEn)) {
                return p;
            }
        }
        return null;
    }

    public static PlatformInfo getPlatformInfoByUrl(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        url = CommonUtil.getSimpleUrl(url);
        for (PlatformInfo p : PlatformInfo.values()) {
            if (url.toLowerCase().contains(p.getNameEn().toLowerCase())) {
                return p;
            }
        }
        return null;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }


}
