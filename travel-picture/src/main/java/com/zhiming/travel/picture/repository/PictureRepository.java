package com.zhiming.travel.picture.repository;

import com.zhiming.travel.picture.domain.PictureDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HuangZhiMing
 */
@Mapper
@Repository
public interface PictureRepository {
    int insertNewPicture(PictureDO record);
    PictureDO selectPictureById(Long pictureId);
    int isPictureExist(Long pictureId);
    int deletePictureById(Long pictureId);
    int isPictureDisable(Long pictureId);
    int makePictureEnable(Long pictureId);
    int makePictureDisable(Long pictureId);
    int isPictureIdExist(Long[] pictures);
    List<PictureDO> selectPictures(Long[] pictures);
}