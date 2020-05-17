package com.zhiming.travel.picture.service.impl;

import com.zhiming.travel.api.dto.picture.PictureDTO;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.IdGenerator;
import com.zhiming.travel.picture.bo.PictureBO;
import com.zhiming.travel.picture.domain.PictureDO;
import com.zhiming.travel.picture.dto.FileDTO;
import com.zhiming.travel.picture.repository.PictureRepository;
import com.zhiming.travel.picture.service.AliYunOSSService;
import com.zhiming.travel.picture.service.PictureService;
import com.zhiming.travel.picture.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangZhiMing
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Value("${travel.service.serviceId}")
    private Long serviceId;

    @Value("${travel.service.machineId}")
    private Long machineId;

    private final AliYunOSSService aliYunOSSService;

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureServiceImpl(AliYunOSSService aliYunOSSService, PictureRepository pictureRepository) {
        this.aliYunOSSService = aliYunOSSService;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public PictureDTO obtainSinglePicture(Long pictureId) {
        PictureDO pictureDO = pictureRepository.selectPictureById(pictureId);
        if (pictureDO.getStatus() == 1) {
            throw new TravelServiceException(ResultEnum.CAN_NOT_ACCESS);
        }
        if (pictureDO.getIsDelete() == 1) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        PictureDTO result = new PictureDTO();
        BeanUtils.copyProperties(pictureDO, result);
        return result;
    }

    @Override
    public List<PictureDTO> obtainMultiplePicture(Long[] pictures) {
        int isPicturesExist = pictureRepository.isPictureIdExist(pictures);
        if (isPicturesExist != pictures.length) {
            throw new TravelServiceException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
        List<PictureDO> pictureDOS = pictureRepository.selectPictures(pictures);
        List<PictureDTO> pictureDTOS = new ArrayList<>();
        for (PictureDO pictureDO : pictureDOS) {
            PictureDTO pictureDTO = new PictureDTO();
            BeanUtils.copyProperties(pictureDO, pictureDTO);
            pictureDTOS.add(pictureDTO);
        }
        return pictureDTOS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public PictureDTO uploadSinglePicture(MultipartFile picture) throws IOException {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFile(picture);
        String fileName = FileUtil.genUUIDFileName(picture);
        fileDTO.setName(fileName);
        String url = aliYunOSSService.uploadFileToAliYunOSS(fileDTO);
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        Long pictureId = idGenerator.nextId();
        PictureDO record = new PictureDO();
        PictureBO pictureInfo = FileUtil.parsePictureInfo(picture);
        BeanUtils.copyProperties(pictureInfo, record);
        record.setUrl(url);
        record.setPictureId(pictureId);
        record.setName(fileName);
        pictureRepository.insertNewPicture(record);
        return obtainSinglePicture(pictureId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<PictureDTO> uploadMultiplePicture(MultipartFile[] pictures) throws IOException {
        System.out.println(pictures.length);
        List<PictureDTO> pictureDTOS = new ArrayList<>();
        for (MultipartFile picture : pictures) {
            PictureDTO pictureDTO = uploadSinglePicture(picture);
            pictureDTOS.add(pictureDTO);
        }
        return pictureDTOS;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void makePictureDisable(Long pictureId) {
        int isPictureExist = pictureRepository.isPictureExist(pictureId);
        if (isPictureExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        pictureRepository.makePictureDisable(pictureId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void makePictureEnable(Long pictureId) {
        int isPictureExist = pictureRepository.isPictureExist(pictureId);
        if (isPictureExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        pictureRepository.makePictureEnable(pictureId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deletePicture(Long pictureId) {
        int isPictureExist = pictureRepository.isPictureExist(pictureId);
        if (isPictureExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        pictureRepository.deletePictureById(pictureId);
    }
}
