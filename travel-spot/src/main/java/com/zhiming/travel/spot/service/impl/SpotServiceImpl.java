package com.zhiming.travel.spot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhiming.travel.api.client.CollectClient;
import com.zhiming.travel.api.client.PictureClient;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.picture.PictureDTO;
import com.zhiming.travel.api.dto.spot.SimpleSpotDTO;
import com.zhiming.travel.api.dto.spot.SpotCoverDTO;
import com.zhiming.travel.api.dto.spot.SpotDTO;
import com.zhiming.travel.api.dto.spot.SpotLocationDTO;
import com.zhiming.travel.api.form.spot.PostSpotForm;
import com.zhiming.travel.api.form.spot.UpdateSpotForm;
import com.zhiming.travel.api.query.spot.SpotCoverQuery;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.IdGenerator;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.spot.domain.RegionDO;
import com.zhiming.travel.spot.domain.SpotDO;
import com.zhiming.travel.spot.domain.SpotPictureDO;
import com.zhiming.travel.spot.dto.AMapLocationDTO;
import com.zhiming.travel.spot.repository.*;
import com.zhiming.travel.spot.service.AMapService;
import com.zhiming.travel.spot.service.SpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangZhiMing
 */
@Service
public class SpotServiceImpl implements SpotService {

    @Value("${travel.service.serviceId}")
    private Long serviceId;
    @Value("${travel.service.machineId}")
    private Long machineId;
    private final AMapService aMapService;
    private final PictureClient pictureClient;
    private final CollectClient collectClient;
    private final SpotRepository spotRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final SpotTypeRepository spotTypeRepository;
    private final SpotPictureRepository spotPictureRepository;

    @Autowired
    public SpotServiceImpl(
            SpotRepository spotRepository,
            RegionRepository regionRepository,
            SpotTypeRepository spotTypeRepository,
            AMapService aMapService, PictureClient pictureClient,
            CollectClient collectClient, SpotPictureRepository spotPictureRepository,
            CountryRepository countryRepository
    ) {

        this.aMapService = aMapService;
        this.pictureClient = pictureClient;
        this.collectClient = collectClient;
        this.spotRepository = spotRepository;
        this.regionRepository = regionRepository;
        this.countryRepository = countryRepository;
        this.spotTypeRepository = spotTypeRepository;
        this.spotPictureRepository = spotPictureRepository;
    }

    @Override
    public SpotDTO obtainSpot(Long spotId) throws JsonProcessingException {
        SpotDO spotDO = spotRepository.selectBySpotId(spotId);
        SpotDTO item = new SpotDTO();
        BeanUtils.copyProperties(spotDO, item);
        if (spotDO.getCountry() != null) {
            item.setCountry(countryRepository.selectNameById(spotDO.getCountry()));
        }
        if (spotDO.getProvince() != null) {
            item.setProvince(regionRepository.selectNameById(spotDO.getProvince()));
        }
        if (spotDO.getCity() != null) {
            item.setCity(regionRepository.selectNameById(spotDO.getCity()));
        }
        if (spotDO.getArea() != null) {
            item.setArea(regionRepository.selectNameById(spotDO.getArea()));
        }
        List<Long> pictureIds = spotPictureRepository.selectPictureBySpot(spotDO.getSpotId());
        List<PictureDTO> pictures = new ArrayList<>();
        for (Long pictureId : pictureIds) {
            Result<PictureDTO> pictureResult = pictureClient.fetchSinglePictureInfo(pictureId);
            PictureDTO pictureDTO = ResultUtil.parsingData(pictureResult);
            pictures.add(pictureDTO);
        }
        Result<Integer> collectCountResult = collectClient.fetchCollectCount(item.getSpotId());
        Integer collectCount = ResultUtil.parsingData(collectCountResult);
        item.setStarNum(collectCount);
        item.setPictures(pictures);
        return item;
    }

    @Override
    public PageDTO obtainSpotCovers(Long userId, SpotCoverQuery query) {
        int boundary = query.getBoundary();
        int offset = query.getOffset() * 2;
        SpotDO record = new SpotDO();
        BeanUtils.copyProperties(query, record);
        List<SpotDO> spotDOS = spotRepository.selectSpot(boundary, offset, record);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setBoundary(boundary);
        pageDTO.setOffset(query.getOffset());
        if (spotDOS.size() > query.getOffset()) {
            pageDTO.setHasNext(true);
            spotDOS = spotDOS.subList(0, query.getOffset());
        } else {
            pageDTO.setHasNext(false);
        }
        List<SpotCoverDTO> results = new ArrayList<>();
        for (SpotDO item : spotDOS) {
            SpotCoverDTO spotCoverDTO = new SpotCoverDTO();
            BeanUtils.copyProperties(item, spotCoverDTO);
            if (item.getArea() != null) {
                String area = regionRepository.selectNameById(item.getArea());
                spotCoverDTO.setArea(area);
            }
            Long pictureId = spotPictureRepository.selectCoverPictureId(item.getSpotId());
            Result<PictureDTO> pictureResult = pictureClient.fetchSinglePictureInfo(pictureId);
            PictureDTO pictureDTO = ResultUtil.parsingData(pictureResult);
            Result<Integer> collectCountResult = collectClient.fetchCollectCount(item.getSpotId());
            Integer collectCount = ResultUtil.parsingData(collectCountResult);
            Result<Boolean> isCollectResult = collectClient.isCollect(userId, item.getSpotId());
            Boolean isCollect = ResultUtil.parsingData(isCollectResult);
            spotCoverDTO.setStarNum(collectCount);
            spotCoverDTO.setIsStar(isCollect);
            spotCoverDTO.setCover(pictureDTO.getUrl());
            results.add(spotCoverDTO);
        }
        pageDTO.setData(results);
        return pageDTO;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void postNewSpot(PostSpotForm form) throws JsonProcessingException {
        int isSpotTypeExist = spotTypeRepository.isSpotTypeExist(form.getType());
        if (isSpotTypeExist == 0) {
            throw new TravelServiceException(ResultEnum.SPOT_TYPE_NOT_EXIST);
        }
        AMapLocationDTO location = aMapService.obtainLocationByAddress(form.getAddress());
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        Long spotId = idGenerator.nextId();
        SpotDO record = new SpotDO();
        BeanUtils.copyProperties(form, record);
        record.setSpotId(spotId);
        record.setLongitude(location.getLongitude());
        record.setLatitude(location.getLatitude());
        Result<PictureDTO> coverVO = pictureClient.uploadSinglePicture(form.getCover());
        PictureDTO pictureDTO = ResultUtil.parsingData(coverVO);
        SpotPictureDO coverPicture = new SpotPictureDO(spotId, pictureDTO.getPictureId(), 1);
        List<SpotPictureDO> pictureDOS = new ArrayList<>();
        pictureDOS.add(coverPicture);
        if (form.getPics() != null) {
            if (form.getPics().length > 0) {
                Result<List<PictureDTO>> spotPicsVO = pictureClient.uploadMulPicture(form.getPics());
                List<PictureDTO> pictureDTOS = ResultUtil.parsingData(spotPicsVO);
                for (PictureDTO pic : pictureDTOS) {
                    SpotPictureDO item = new SpotPictureDO(spotId, pic.getPictureId(), 0);
                    pictureDOS.add(item);
                }
            }
        }
        spotPictureRepository.insertMany(pictureDOS);
        spotRepository.insertNewSpot(record);
    }

    @Override
    public void updateSpot(UpdateSpotForm form) {
        int isSpotExist = spotRepository.isSpotExist(form.getSpotId());
        if (isSpotExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        SpotDO record = new SpotDO();
        BeanUtils.copyProperties(form, record);
        spotRepository.updateSpot(record);
    }

    @Override
    public boolean checkSpotExist(Long spotId) {
        int isSpotExist = spotRepository.isSpotExist(spotId);
        return isSpotExist != 0;
    }

    @Override
    public SpotLocationDTO obtainSpotLocation(Long spotId) {
        SpotDO spotDO = spotRepository.selectBySpotId(spotId);
        SpotLocationDTO item = new SpotLocationDTO();
        BeanUtils.copyProperties(spotDO, item);
        if (spotDO.getCountry() != null) {
            item.setCountryId(spotDO.getCountry());
            item.setCountryName(countryRepository.selectNameById(spotDO.getCountry()));
        }
        if (spotDO.getProvince() != null) {
            item.setProvince(spotDO.getProvince());
            item.setProvinceName(regionRepository.selectNameById(spotDO.getProvince()));
        }
        if (spotDO.getCity() != null) {
            item.setCity(spotDO.getCity());
            item.setCityName(regionRepository.selectNameById(spotDO.getCity()));
        }
        if (spotDO.getArea() != null) {
            item.setArea(spotDO.getArea());
            item.setAreaName(regionRepository.selectNameById(spotDO.getArea()));
        }
        return item;
    }

    @Override
    public String obtainSpotName(Long spotId) {
        return spotRepository.selectSpotName(spotId);
    }

    @Override
    public List<SimpleSpotDTO> obtainSimpleSpots(Integer city) {
        List<SpotDO> spotDOS = spotRepository.selectSpotByCity(city);
        List<SimpleSpotDTO> simpleSpotDTOS = new ArrayList<>();
        for (SpotDO spotDO : spotDOS) {
            SimpleSpotDTO simpleSpotDTO = new SimpleSpotDTO();
            simpleSpotDTO.setSpotId(spotDO.getSpotId());
            simpleSpotDTO.setSpotName(spotDO.getSpotName());
            simpleSpotDTO.setAddress(spotDO.getAddress());
            RegionDO regionDO = regionRepository.selectByCity(city);
            simpleSpotDTO.setCityId(city);
            simpleSpotDTO.setCityName(regionDO.getRegionName());
            simpleSpotDTOS.add(simpleSpotDTO);
        }
        return simpleSpotDTOS;
    }
}
