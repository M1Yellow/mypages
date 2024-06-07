package cn.m1yellow.mypages.common.service.impl;

import cn.m1yellow.mypages.common.service.FileDownloadService;
import cn.m1yellow.mypages.common.util.HttpClientUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("httpClientDownloadService")
public class HttpClientDownloadServiceImpl implements FileDownloadService {

    @Override
    public void singleFileDownload(String fileUrl, String fileName, String saveDir, Map<String, Object> params) {

        HttpClientUtil.singleFileDownload(fileUrl, fileName, saveDir, params);
    }
}
