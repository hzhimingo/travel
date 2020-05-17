package com.zhiming.travel.moment.service.impl;

import com.zhiming.travel.api.client.*;
import com.zhiming.travel.api.dto.cr.CommentCoverDTO;
import com.zhiming.travel.api.dto.moment.MomentCoverDTO;
import com.zhiming.travel.api.dto.moment.MomentDetailDTO;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.picture.PictureDTO;
import com.zhiming.travel.api.dto.spot.SpotLocationDTO;
import com.zhiming.travel.api.dto.user.SimpleUserDTO;
import com.zhiming.travel.api.form.moment.PostMomentForm;
import com.zhiming.travel.api.query.moment.MomentCoverQuery;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.IdGenerator;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.moment.domain.MomentDO;
import com.zhiming.travel.moment.domain.MomentPictureDO;
import com.zhiming.travel.moment.domain.MomentTopicDO;
import com.zhiming.travel.moment.repository.MomentPictureRepository;
import com.zhiming.travel.moment.repository.MomentRepository;
import com.zhiming.travel.moment.repository.MomentTopicRepository;
import com.zhiming.travel.moment.service.MomentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MomentServiceImpl implements MomentService {

    @Value("${travel.service.serviceId}")
    private Long serviceId;
    @Value("${travel.service.machineId}")
    private Long machineId;

    private final MomentPictureRepository momentPictureRepository;
    private final MomentTopicRepository momentTopicRepository;
    private final MomentRepository momentRepository;
    private final PictureClient pictureClient;
    private final SpotClient spotClient;
    private final TopicClient topicClient;
    private final UserClient userClient;
    private final CollectClient collectClient;
    private final ThumbUpClient thumbUpClient;
    private final CRClient crClient;

    @Autowired
    public MomentServiceImpl(
            TopicClient topicClient, SpotClient spotClient,
            PictureClient pictureClient, MomentRepository momentRepository,
            MomentPictureRepository momentPictureRepository,
            MomentTopicRepository momentTopicRepository,
            UserClient userClient, CollectClient collectClient, ThumbUpClient thumbUpClient, CRClient crClient) {
        this.topicClient = topicClient;
        this.spotClient = spotClient;
        this.pictureClient = pictureClient;
        this.momentRepository = momentRepository;
        this.momentTopicRepository = momentTopicRepository;
        this.momentPictureRepository = momentPictureRepository;
        this.userClient = userClient;
        this.collectClient = collectClient;
        this.thumbUpClient = thumbUpClient;
        this.crClient = crClient;
    }

    @Override
    public PageDTO obtainMomentCovers(Long userId, MomentCoverQuery query) {
        int boundary = query.getBoundary();
        int offset = query.getOffset() * 2;
        //设置偏移量时，设置为2倍的offset, 以便后续检查是否有下一页
        MomentDO record = new MomentDO();
        BeanUtils.copyProperties(query, record);
        List<MomentDO> momentDOS = momentRepository.selectMomentCover(boundary, offset, record);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setBoundary(boundary);
        pageDTO.setOffset(query.getOffset());
        if (momentDOS.size() > query.getOffset()) {
            pageDTO.setHasNext(true);
            momentDOS = momentDOS.subList(0, query.getOffset());
        } else {
            pageDTO.setHasNext(false);
        }
        List<MomentCoverDTO> results = new ArrayList<>();
        for (MomentDO item : momentDOS) {
            //获取封面图信息
            Long pictureId = momentPictureRepository.selectCoverPicture(item.getMomentId());
            Result<PictureDTO> pictureVO = pictureClient.fetchSinglePictureInfo(pictureId);
            PictureDTO pictureDTO = ResultUtil.parsingData(pictureVO);
            //获取点赞数，是否点赞
            Result<String> spotVO = spotClient.obtainSpotName(item.getSpot());
            String location = ResultUtil.parsingData(spotVO);
            Result<SimpleUserDTO> userVO = userClient.fetchSimpleUserInfo(item.getAuthor());
            SimpleUserDTO simpleUserDTO = ResultUtil.parsingData(userVO);
            String authorAvatar = simpleUserDTO.getAvatar();
            String authorNickname = simpleUserDTO.getNickname();
            Result<Integer> thumbUpNumVO = thumbUpClient.fetchThumbUpNum(item.getMomentId());
            Integer thumbUpNum = ResultUtil.parsingData(thumbUpNumVO);
            MomentCoverDTO result = new MomentCoverDTO();
            BeanUtils.copyProperties(item, result);
            if (userId != null) {
                Result<Boolean> isThumbUpResult = thumbUpClient.isThumbUp(userId, item.getMomentId());
                Boolean isThumbUp = ResultUtil.parsingData(isThumbUpResult);
                result.setIsFavorite(isThumbUp);
            } else {
                result.setIsFavorite(false);
            }
            result.setFavoriteNum(thumbUpNum);
            result.setCoverText(item.getTitle());
            result.setLocation(location);
            result.setCoverImage(pictureDTO);
            result.setAuthorAvatar(authorAvatar);
            result.setAuthorName(authorNickname);
            results.add(result);
        }
        pageDTO.setData(results);
        return pageDTO;
    }

    @Override
    public MomentDetailDTO obtainMomentDetail(Long momentId, Long userId) {
        MomentDO momentDO = momentRepository.selectMomentById(momentId);
        if (momentDO == null) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        //获取评论以及评论数
        //发送的请求过多，可以考虑异步的方式
        Result<Integer> commentCountVO = crClient.fetchCommentCount(momentId);
        Integer commentCount = ResultUtil.parsingData(commentCountVO);
        Result<List<CommentCoverDTO>> commentCoverDTOVO = crClient.fetchCommentCover(momentId);
        List<CommentCoverDTO> commentCoverDTOS = ResultUtil.parsingData(commentCoverDTOVO);
        Result<SimpleUserDTO> userVO = userClient.fetchSimpleUserInfo(momentDO.getAuthor());
        SimpleUserDTO simpleUserDTO = ResultUtil.parsingData(userVO);
        Result<Integer> collectCountVO = collectClient.fetchCollectCount(momentId);
        Integer collectCount = ResultUtil.parsingData(collectCountVO);
        Result<Integer> thumbUpCountVO = thumbUpClient.fetchThumbUpNum(momentId);
        Integer thumbUpCount = ResultUtil.parsingData(thumbUpCountVO);
        Long authorId = simpleUserDTO.getUserId();
        String authorAvatar = simpleUserDTO.getAvatar();
        String authorNickname = simpleUserDTO.getNickname();
        List<Long> pictureIdsList = momentPictureRepository.selectMomentPicture(momentId);
        Result<List<PictureDTO>> pictureDTOResult = pictureClient.fetchMulPictureInfo(pictureIdsList.toArray(new Long[0]));
        List<PictureDTO> pictures = ResultUtil.parsingData(pictureDTOResult);
        MomentDetailDTO result = new MomentDetailDTO();
        if (userId != null) {
            //查询是否点赞
            Result<Boolean> isThumbUpResult = thumbUpClient.isThumbUp(userId, momentId);
            Boolean isThumbUp = ResultUtil.parsingData(isThumbUpResult);
            //查询是否收藏
            Result<Boolean> isCollectResult = collectClient.isCollect(userId, momentId);
            Boolean isCollect = ResultUtil.parsingData(isCollectResult);
            result.setIsFav(isThumbUp);
            result.setIsCollect(isCollect);
        } else {
            result.setIsFav(false);
            result.setIsCollect(false);
        }
        Result<SpotLocationDTO> spotLocationDTOResult = spotClient.obtainSpotPoint(momentDO.getSpot());
        SpotLocationDTO spotLocationDTO = ResultUtil.parsingData(spotLocationDTOResult);
        BeanUtils.copyProperties(momentDO, result);
        result.setSpot(spotLocationDTO);
        result.setReleaseDate(momentDO.getCreateTime().toLocalDate());
        result.setPictures(pictures);
        result.setAuthorId(authorId);
        result.setAvatar(authorAvatar);
        result.setNickname(authorNickname);
        result.setStarNum(collectCount);
        result.setFavNum(thumbUpCount);
        result.setCommentNum(commentCount);
        result.setComments(commentCoverDTOS);
        return result;
    }

    @Override
    public void addNewMoment(PostMomentForm form) {
        Result<Boolean> isSpotVO = spotClient.isSpotExist(form.getSpot());
        Boolean isSpotExist = ResultUtil.parsingData(isSpotVO);
        if (!isSpotExist) {
            throw new TravelServiceException(ResultEnum.SPOT_TYPE_NOT_EXIST);
        }
        //上传图片
        Result<List<PictureDTO>> uploadVO = pictureClient.uploadMulPicture(form.getPics());
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        Long momentId = idGenerator.nextId();
        List<PictureDTO> pictures = ResultUtil.parsingData(uploadVO);
        System.out.println("pictureDTO" + pictures.size());
        List<MomentPictureDO> momentPictureDOS = new ArrayList<>();
        for (int i = 0; i < pictures.size(); i++) {
            MomentPictureDO momentPictureDO = new MomentPictureDO();
            momentPictureDO.setMomentId(momentId);
            momentPictureDO.setPictureId(pictures.get(i).getPictureId());
            if (i == 0) {
                momentPictureDO.setIsCover(1);
            } else {
                momentPictureDO.setIsCover(0);
            }
            momentPictureDOS.add(momentPictureDO);
        }
        momentPictureRepository.insertMomentPictures(momentPictureDOS);
        //查看是否有话题，并进行检查合法性,最后进行
        if (form.getTopics() != null) {
            if (form.getTopics().length != 0) {
                Result<Boolean> isTopicVO = topicClient.checkTopicsExist(form.getTopics());
                Boolean isTopicsExist = ResultUtil.parsingData(isTopicVO);
                if (isTopicsExist) {
                    List<MomentTopicDO> momentTopicDOS = new ArrayList<>();
                    for (Long topic : form.getTopics()) {
                        MomentTopicDO momentTopicItem = new MomentTopicDO();
                        momentTopicItem.setTopicId(topic);
                        momentTopicItem.setMomentId(momentId);
                        momentTopicDOS.add(momentTopicItem);
                    }
                    momentTopicRepository.insertMomentTopics(momentTopicDOS);
                }
            }
        }
        //写入数据库
        MomentDO record = new MomentDO();
        record.setMomentId(momentId);
        record.setAuthor(form.getUserId());
        record.setSpot(form.getSpot());
        record.setTitle(form.getTitle());
        record.setContent(form.getContent());
        record.setCountry(1);
        record.setCity(1);
        momentRepository.insertNewMoment(record);
    }

    @Override
    public void deleteMoment(Long momentId) {
        int isMomentExist = momentRepository.isMomentExist(momentId);
        if (isMomentExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        momentRepository.deleteMomentById(momentId);
    }

    @Override
    public void makeMomentEnable(Long momentId) {
        int isMomentExist = momentRepository.isMomentExist(momentId);
        if (isMomentExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        momentRepository.updateMomentStatusToNormal(momentId);
    }

    @Override
    public void makeMomentDisable(Long momentId) {
        int isMomentExist = momentRepository.isMomentExist(momentId);
        if (isMomentExist == 0) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        momentRepository.updateMomentStatusToDisable(momentId);
    }
}
