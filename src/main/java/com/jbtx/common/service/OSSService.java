package com.jbtx.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiaoyou
 * @title: OSSService
 * @projectName financial
 * @description: 阿里云OSS文件上传
 * @date 2019/6/1510:38
 */
public interface OSSService {
    /**
     * 上传文件<Byte 数组格式>
     * @param filePathName
     * @param fileByte
     */
    void uploadFileByByte(String filePathName, byte[] fileByte);

    /**
     * 分片上传
     * @param filePathName
     * @param file
     * @throws IOException
     */
    void uploadFileFragmentation(String filePathName, final MultipartFile file) throws IOException;

    /**
     * 上传文件<本地文件></本地文件>
     * @param filePathName
     * @param file
     */
    void uploadFileByFile(String filePathName, File file);

    /**
     * 上产文件<文件流>
     * @param filePathName
     */
    void uploadFileByInputStream(String filePathName, InputStream inputStream);

    /**
     * 获取授权文件地址
     * @param filePathName
     * @return
     */
    String getPerFileUrl(String filePathName);
}
