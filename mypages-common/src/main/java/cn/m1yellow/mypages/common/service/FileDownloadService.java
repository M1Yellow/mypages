package cn.m1yellow.mypages.common.service;

import java.util.Map;

public interface FileDownloadService {

    void singleFileDownload(String fileUrl, String fileName, String saveDir, Map<String, Object> params);

}
