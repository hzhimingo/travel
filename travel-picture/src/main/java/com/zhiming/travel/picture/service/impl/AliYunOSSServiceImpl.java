package com.zhiming.travel.picture.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.picture.dto.FileDTO;
import com.zhiming.travel.picture.service.AliYunOSSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author HuangZhiMing
 */
@Service
public class AliYunOSSServiceImpl implements AliYunOSSService {

    @Value("${aliYun.oss.endpoint}")
    private String endpoint;

    @Value("${aliYun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliYun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliYun.oss.bucketName}")
    private String bucketName;

    @Override
    public String uploadFileToAliYunOSS(FileDTO fileDTO) throws IOException {
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String key = null;
        if (fileDTO.getDir() == null) {
            key = fileDTO.getName();
        } else {
            key = fileDTO.getDir() + fileDTO.getName();
        }
        PutObjectResult result = oss.putObject(bucketName, key, fileDTO.getFile().getInputStream());
        if (result == null) {
            throw new TravelServiceException(ResultEnum.FILE_UPLOAD_FAILED);
        }
        return "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com" + "/" + key;
    }

    @Override
    public List<String> uploadMultipleFileToAliYunOSS(List<FileDTO> fileDTOS) {
        return null;
    }
}
