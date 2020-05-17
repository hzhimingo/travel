package com.zhiming.travel.picture.service;

import com.zhiming.travel.picture.dto.FileDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author HuangZhiMing
 */
public interface AliYunOSSService {
    /**
     * @param fileDTO 存储信息
     * @return String
     * 上传单个文件到阿里云对象存储
     */
    String uploadFileToAliYunOSS(FileDTO fileDTO) throws IOException;

    /**
     * @param fileDTOS 存储信息
     * @return List<String>
     * 上传多张文件到阿里云对象存储
     */
    List<String> uploadMultipleFileToAliYunOSS(List<FileDTO> fileDTOS);
}
