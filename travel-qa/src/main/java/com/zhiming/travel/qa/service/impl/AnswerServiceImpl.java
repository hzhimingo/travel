package com.zhiming.travel.qa.service.impl;

import com.zhiming.travel.api.client.*;
import com.zhiming.travel.api.dto.cr.CommentCoverDTO;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.picture.PictureDTO;
import com.zhiming.travel.api.dto.qa.AnswerCoverDTO;
import com.zhiming.travel.api.dto.qa.AnswerDetailDTO;
import com.zhiming.travel.api.dto.qa.AnswerInnerDTO;
import com.zhiming.travel.api.dto.user.SimpleUserDTO;
import com.zhiming.travel.api.form.qa.PostAnswerForm;
import com.zhiming.travel.api.query.qa.AnswerCoverQuery;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.IdGenerator;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.qa.domain.AnswerDO;
import com.zhiming.travel.qa.domain.AnswerPictureDO;
import com.zhiming.travel.qa.repository.AnswerPictureRepository;
import com.zhiming.travel.qa.repository.AnswerRepository;
import com.zhiming.travel.qa.service.AnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {


    @Value("${travel.answer.serviceId}")
    private Long serviceId;
    @Value("${travel.answer.machineId}")
    private Long machineId;
    private final CRClient crClient;
    private final ThumbUpClient thumbUpClient;
    private final CollectClient collectClient;
    private final UserClient userClient;
    private final PictureClient pictureClient;
    private final AnswerPictureRepository answerPictureRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(
            CRClient crClient, ThumbUpClient thumbUpClient, CollectClient collectClient, UserClient userClient, PictureClient pictureClient,
            AnswerPictureRepository answerPictureRepository,
            AnswerRepository answerRepository
    ) {
        this.crClient = crClient;
        this.thumbUpClient = thumbUpClient;
        this.collectClient = collectClient;
        this.userClient = userClient;
        this.pictureClient = pictureClient;
        this.answerPictureRepository = answerPictureRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postNewAnswer(PostAnswerForm form) {
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        Long answerId = idGenerator.nextId();
        AnswerDO record = new AnswerDO();
        record.setAnswerId(answerId);
        record.setAuthor(form.getUserId());
        record.setQuestion(form.getQuestionId());
        record.setContent(form.getContent());
        answerRepository.insert(record);
        if (form.getPics() != null) {
            if (form.getPics().length != 0) {
                Result<List<PictureDTO>> picturesUploadResult = pictureClient.uploadMulPicture(form.getPics());
                List<PictureDTO> pictureDTOS = ResultUtil.parsingData(picturesUploadResult);
                List<AnswerPictureDO> pictures = new ArrayList<>();
                for (PictureDTO pictureDTO : pictureDTOS) {
                   AnswerPictureDO answerPictureDO = new AnswerPictureDO();
                   answerPictureDO.setAnswerId(answerId);
                   answerPictureDO.setPictureId(pictureDTO.getPictureId());
                   pictures.add(answerPictureDO);
                }
                answerPictureRepository.insertAnswerPictures(pictures);
            }
        }
    }

    @Override
    public AnswerInnerDTO obtainAnswerInners(Long questionId) {
        AnswerDO answer = answerRepository.selectAnswerByQuestionId(questionId);
        if (answer != null) {
            AnswerInnerDTO answerInner = new AnswerInnerDTO();
            SimpleUserDTO simpleUserDTO = obtainSimpleUserDTO(answer.getAuthor());
            answerInner.setAnswerId(answer.getAnswerId());
            answerInner.setAuthorId(simpleUserDTO.getUserId());
            answerInner.setAvatar(simpleUserDTO.getAvatar());
            answerInner.setNickname(simpleUserDTO.getNickname());
            answerInner.setContent(answer.getContent());
            List<PictureDTO> pictures = obtainPictures(answer.getAnswerId());
            answerInner.setPictures(pictures);
            return answerInner;
        }
        return null;
    }

    @Override
    public PageDTO obtainAnswerCovers(Long userId, AnswerCoverQuery query) {
        int boundary = query.getBoundary();
        int offset = query.getOffset() * 2;
        AnswerDO record = new AnswerDO();
        BeanUtils.copyProperties(query, record);
        record.setStatus(0);
        record.setIsDelete(0);
        List<AnswerDO> answers = answerRepository.selectAnswerCovers(boundary, offset, record);
        PageDTO page = new PageDTO();
        page.setBoundary(boundary);
        page.setOffset(query.getOffset());
        if (answers.size() > query.getOffset()) {
            page.setHasNext(true);
            answers = answers.subList(0, query.getOffset());
        } else {
            page.setHasNext(false);
        }
        List<AnswerCoverDTO> answerCovers = new ArrayList<>();
        for (AnswerDO answer : answers) {
            AnswerCoverDTO answerCover = new AnswerCoverDTO();
            BeanUtils.copyProperties(answer, answerCover);
            answerCover.setDate(answer.getCreateTime().toLocalDate());
            SimpleUserDTO simpleUserDTO = obtainSimpleUserDTO(answer.getAuthor());
            answerCover.setAnswerId(answer.getAnswerId());
            answerCover.setAuthorId(simpleUserDTO.getUserId());
            answerCover.setAvatar(simpleUserDTO.getAvatar());
            answerCover.setNickname(simpleUserDTO.getNickname());
            List<PictureDTO> pictures = obtainPictures(answer.getAnswerId());
            answerCover.setPictures(pictures);
            Result<Integer> collectNumResult = collectClient.fetchCollectCount(answer.getAnswerId());
            Integer collectNum = ResultUtil.parsingData(collectNumResult);
            Result<Integer> thumbUpNumResult = thumbUpClient.fetchThumbUpNum(answer.getAnswerId());
            Integer thumbUpNum = ResultUtil.parsingData(thumbUpNumResult);
            Result<Integer> commentNumResult = crClient.fetchCommentCount(answer.getAnswerId());
            Integer commentNum = ResultUtil.parsingData(commentNumResult);
            answerCover.setCommentNum(commentNum);
            answerCover.setStarNum(collectNum);
            answerCover.setFavNum(thumbUpNum);
            if (userId != null) {
                Result<Boolean> isCollectResult = collectClient.isCollect(userId, answer.getAnswerId());
                Boolean isCollect = ResultUtil.parsingData(isCollectResult);
                answerCover.setIsStar(isCollect);
                Result<Boolean> isThumbUpResult = thumbUpClient.isThumbUp(userId, answer.getAnswerId());
                Boolean isThumbUp = ResultUtil.parsingData(isThumbUpResult);
                answerCover.setIsFav(isThumbUp);
            } else {
                answerCover.setIsStar(false);
                answerCover.setIsFav(false);
            }
            answerCovers.add(answerCover);
        }
        page.setData(answerCovers);
        return page;
    }

    public SimpleUserDTO obtainSimpleUserDTO(Long userId) {
        Result<SimpleUserDTO> userVO = userClient.fetchSimpleUserInfo(userId);
        return ResultUtil.parsingData(userVO);
    }

    public List<PictureDTO> obtainPictures(Long answerId) {
        List<Long> pictureIds = answerPictureRepository.selectPicturesByAnswer(answerId);
        List<PictureDTO> pictures = new ArrayList<>();
        for (Long pictureId : pictureIds) {
            Result<PictureDTO> pictureResult = pictureClient.fetchSinglePictureInfo(pictureId);
            PictureDTO pictureDTO = ResultUtil.parsingData(pictureResult);
            pictures.add(pictureDTO);
        }
        return pictures;
    }

    @Override
    public AnswerDetailDTO obtainAnswerDetail(Long answerId, Long userId) {
        AnswerDO answer = answerRepository.selectByPrimaryKey(answerId);
        if (answer == null) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        AnswerDetailDTO answerDetailDTO = new AnswerDetailDTO();
        BeanUtils.copyProperties(answer, answerDetailDTO);
        answerDetailDTO.setDate(answer.getCreateTime().toLocalDate());
        SimpleUserDTO simpleUserDTO = obtainSimpleUserDTO(answer.getAuthor());
        answerDetailDTO.setAuthorId(simpleUserDTO.getUserId());
        answerDetailDTO.setAvatar(simpleUserDTO.getAvatar());
        answerDetailDTO.setNickname(simpleUserDTO.getNickname());
        List<PictureDTO> pictures = obtainPictures(answer.getAnswerId());
        Result<Integer> collectNumResult = collectClient.fetchCollectCount(answerId);
        Integer collectNum = ResultUtil.parsingData(collectNumResult);
        Result<Integer> thumbUpNumResult = thumbUpClient.fetchThumbUpNum(answerId);
        Integer thumbUpNum = ResultUtil.parsingData(thumbUpNumResult);
        Result<List<CommentCoverDTO>> commentCoverDTOResult = crClient.fetchCommentCover(answerId);
        List<CommentCoverDTO> commentCoverDTOS = ResultUtil.parsingData(commentCoverDTOResult);
        Result<Integer> commentNumResult = crClient.fetchCommentCount(answerId);
        Integer commentNum = ResultUtil.parsingData(commentNumResult);
        answerDetailDTO.setCommentNum(commentNum);
        answerDetailDTO.setFavNum(thumbUpNum);
        answerDetailDTO.setStarNum(collectNum);
        answerDetailDTO.setPictures(pictures);
        answerDetailDTO.setComments(commentCoverDTOS);
        if (userId != null) {
            Result<Boolean> isCollectResult = collectClient.isCollect(userId, answerId);
            Boolean isCollect = ResultUtil.parsingData(isCollectResult);
            answerDetailDTO.setIsStar(isCollect);
            Result<Boolean> isThumbUpResult = thumbUpClient.isThumbUp(userId, answerId);
            Boolean isThumbUp = ResultUtil.parsingData(isThumbUpResult);
            answerDetailDTO.setIsFav(isThumbUp);
        } else {
            answerDetailDTO.setIsStar(false);
            answerDetailDTO.setIsFav(false);
        }
        return answerDetailDTO;
    }

    @Override
    public int obtainAnswerCount(Long questionId) {
        return answerRepository.selectAnswerCount(questionId);
    }
}
