package cn.m1yellow.mypages.excavation.service;

import cn.m1yellow.mypages.excavation.bo.UserInfoItem;

import java.util.Map;

/**
 * 数据挖掘服务接口
 */
public interface DataExcavateService {

    UserInfoItem singleImageDownloadFromHtml(String fromUrl, String saveDir, Map<String, Object> params);

    UserInfoItem singleImageDownloadFromJson(String fromUrl, String saveDir, Map<String, Object> params);

}
