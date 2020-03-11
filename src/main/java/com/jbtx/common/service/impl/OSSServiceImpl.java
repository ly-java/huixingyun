package com.jbtx.common.service.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.jbtx.common.service.OSSService;
import com.jbtx.config.OSSConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoyou
 * @title: OSSService
 * @projectName financial
 * @description: 阿里云OSS文件上传
 * @date 2019/6/1510:40
 */
@Service
public class OSSServiceImpl implements OSSService {
    private static final Logger logger = LoggerFactory.getLogger(OSSServiceImpl.class);

    @Autowired
    private OSSConfig ossConfig;

    /**
     * 通过byte方式上传
     *
     * @param filePathName
     * @param fileByte
     * @return
     */
    public void uploadFileByByte(String filePathName, byte[] fileByte) {
        long starTime = System.currentTimeMillis();
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        if (!ossClient.doesBucketExist(ossConfig.getBucketName())) {
            ossClient.createBucket(ossConfig.getBucketName());
        }

        PutObjectResult putObjectResult = ossClient.putObject(ossConfig.getBucketName(), filePathName, new ByteArrayInputStream(fileByte));
        long endTime = System.currentTimeMillis();
        logger.info("上传文件耗时：{}S,文件名：{},eTag:{}", (endTime - starTime)/1000, filePathName, putObjectResult.getETag());
        ossClient.shutdown();
    }

    @Override
    public void uploadFileFragmentation(String filePathName,final MultipartFile sampleFile) throws IOException {
        long starTime = System.currentTimeMillis();
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        // 创建InitiateMultipartUploadRequest对象。
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(ossConfig.getBucketName(), filePathName);
        // 如果需要在初始化分片时设置文件存储类型，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // request.setObjectMetadata(metadata);

        // 初始化分片。
        InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);
        // 返回uploadId，它是分片上传事件的唯一标识，您可以根据这个ID来发起相关的操作，如取消分片上传、查询分片上传等。
        String uploadId = upresult.getUploadId();

        // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
        List<PartETag> partETags =  new ArrayList<PartETag>();
        // 计算文件有多少个分片。
        final long partSize = 20 * 1024 * 1024L;   // 20MB
//        final File sampleFile = new File(file.);
        long fileLength = sampleFile.getSize();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        // 遍历分片上传。
        for (int i = 0; i < partCount; i++) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
            InputStream instream = sampleFile.getInputStream();
            // 跳过已经上传的分片。
            instream.skip(startPos);
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(ossConfig.getBucketName());
            uploadPartRequest.setKey(filePathName);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setInputStream(instream);
            // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100KB。
            uploadPartRequest.setPartSize(curPartSize);
            // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
            uploadPartRequest.setPartNumber( i + 1);
            // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
            UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
            // 每次上传分片之后，OSS的返回结果会包含一个PartETag。PartETag将被保存到partETags中。
            partETags.add(uploadPartResult.getPartETag());
        }
        // 创建CompleteMultipartUploadRequest对象。
        // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(ossConfig.getBucketName(), filePathName, uploadId, partETags);
        // 如果需要在完成文件上传的同时设置文件访问权限，请参考以下示例代码。
        // completeMultipartUploadRequest.setObjectACL(CannedAccessControlList.PublicRead);
        // 完成上传。
        CompleteMultipartUploadResult completeMultipartUploadResult = ossClient.completeMultipartUpload(completeMultipartUploadRequest);
        // 关闭OSSClient。
        long endTime = System.currentTimeMillis();
        logger.info("上传文件耗时：{}S,文件名：{},eTag:{}", (endTime - starTime)/1000, filePathName, completeMultipartUploadResult.getETag());
        ossClient.shutdown();
    }

    @Override
    public String getPerFileUrl(String filePathName) {
        String resultUrl = "";
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        // 设置URL过期时间10分钟
        Date expiration = new Date(new Date().getTime() + 600 * 1000);
        GeneratePresignedUrlRequest reques = new GeneratePresignedUrlRequest(ossConfig.getBucketName(), filePathName, HttpMethod.GET);
        reques.setExpiration(expiration);

       /* if (isThum) {
            reques.setProcess("style/thum");
        }*/

        URL url = ossClient.generatePresignedUrl(ossConfig.getBucketName(), filePathName, expiration);
        resultUrl = url.getProtocol() + "://" + url.getHost() + url.getFile();
        logger.info("获取授权地址为：{}", resultUrl);
        return resultUrl;
    }

    @Override
    public void uploadFileByFile(String filePathName, File file) {
        long starTime = System.currentTimeMillis();
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        if (!ossClient.doesBucketExist(ossConfig.getBucketName())) {
            ossClient.createBucket(ossConfig.getBucketName());
        }
        PutObjectResult putObjectResult = ossClient.putObject(ossConfig.getBucketName(), filePathName, file);
        long endTime = System.currentTimeMillis();
        logger.info("上传文件耗时：{}S,文件名：{},eTag:{}", (endTime - starTime)/1000, filePathName, putObjectResult.getETag());
        ossClient.shutdown();
    }

    @Override
    public void uploadFileByInputStream(String filePathName,  InputStream inputStream) {

        long starTime = System.currentTimeMillis();
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        if (!ossClient.doesBucketExist(ossConfig.getBucketName())) {
            ossClient.createBucket(ossConfig.getBucketName());
        }

        PutObjectResult putObjectResult = ossClient.putObject(ossConfig.getBucketName(),filePathName, inputStream);
        long endTime = System.currentTimeMillis();
        logger.info("上传文件耗时：{}S,文件名：{},eTag:{}:", (endTime - starTime)/1000, filePathName, putObjectResult.getETag());
        ossClient.shutdown();

    }

    public static void main(String[] args) {
        OSSClient ossClient = new OSSClient("oss-cn-qingdao.aliyuncs.com", "LTAIyxx8DXlYV8MA", "SUewaiUyYjYjkUNz2svlNwOfvyTqPg");

        // 设置URL过期时间为1小时。
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        GeneratePresignedUrlRequest reques = new GeneratePresignedUrlRequest("sol-dev-bucket", "2019/51/14/201906141836231560508583139.jpg", HttpMethod.GET);
        reques.setProcess("style/thum");
        reques.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(reques);
        System.out.println(url.getProtocol() + "://" + url.getHost() + url.getFile());

    }
}
