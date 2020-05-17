package com.zhiming.travel.picture.service;

import com.zhiming.travel.api.dto.picture.PictureDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author HuangZhiMing
 */
public interface PictureService {
    /**
     * @param pictureId 图片id
     * @return PictureDTO
     * 根据图片id获取图片信息
     */
    PictureDTO obtainSinglePicture(Long pictureId);

    /**
     * @param pictures 图片id
     * @return List<PictureDTO>
     * 根据多张图片id获取多张图片信息
     */
    List<PictureDTO> obtainMultiplePicture(Long[] pictures);

    /**
     * @param picture 图片
     * @return PictureDTO
     * 上传单张图片
     */
    PictureDTO uploadSinglePicture(MultipartFile picture) throws IOException;

    /**
     * @param pictures 多张图片
     * @return List<PictureDTO>
     * 上传多张图片
     */
    List<PictureDTO> uploadMultiplePicture(MultipartFile[] pictures) throws IOException;

    void makePictureDisable(Long pictureId);

    void makePictureEnable(Long pictureId);

    void deletePicture(Long pictureId);
}
