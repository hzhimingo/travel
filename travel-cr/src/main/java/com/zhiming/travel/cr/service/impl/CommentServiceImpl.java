package com.zhiming.travel.cr.service.impl;

import com.zhiming.travel.api.client.UserClient;
import com.zhiming.travel.api.dto.cr.CommentCoverDTO;
import com.zhiming.travel.api.dto.cr.CommentDTO;
import com.zhiming.travel.api.dto.cr.ReplyDTO;
import com.zhiming.travel.api.dto.user.SimpleUserDTO;
import com.zhiming.travel.api.form.cr.PostCommentForm;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.IdGenerator;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.util.TimeUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import com.zhiming.travel.cr.domain.CommentDO;
import com.zhiming.travel.cr.repository.CommentRepository;
import com.zhiming.travel.cr.service.CommentService;
import com.zhiming.travel.cr.service.ReplyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Value("${travel.service.serviceId}")
    private Long serviceId;
    @Value("${travel.service.machineId}")
    private Long machineId;

    private final UserClient userClient;
    private final ReplyService replyService;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ReplyService replyService, UserClient userClient, CommentRepository commentRepository) {
        this.userClient = userClient;
        this.replyService = replyService;
        this.commentRepository = commentRepository;
    }

    @Override
    public void postNewComment(PostCommentForm form) {
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        Long commentId = idGenerator.nextId();
        CommentDO record = new CommentDO();
        BeanUtils.copyProperties(form, record);
        record.setCommentId(commentId);
        record.setAuthor(form.getUserId());
        commentRepository.insertNewComment(record);
    }

    @Override
    public List<CommentCoverDTO> obtainCommentCover(Long serviceBusinessId) {
        List<CommentDO> commentDOS = commentRepository.selectCommentByServiceBusinessId(serviceBusinessId);
        List<CommentCoverDTO> result = new ArrayList<>();
        for (CommentDO commentDO : commentDOS) {
            Result<SimpleUserDTO> simpleUserResult = userClient.fetchSimpleUserInfo(commentDO.getAuthor());
            SimpleUserDTO simpleUserDTO = ResultUtil.parsingData(simpleUserResult);
            CommentCoverDTO coverDTO = new CommentCoverDTO();
            coverDTO.setCommentId(commentDO.getCommentId());
            coverDTO.setAuthorId(simpleUserDTO.getUserId());
            coverDTO.setAuthorNickname(simpleUserDTO.getNickname());
            coverDTO.setAuthorAvatar(simpleUserDTO.getAvatar());
            coverDTO.setContent(commentDO.getContent());
            coverDTO.setTime(TimeUtil.calTimeDuration(commentDO.getCreateTime()));
            result.add(coverDTO);
        }
        return result;
    }

    @Override
    public List<CommentDTO> obtainComments(Long serviceBusinessId) {
        List<CommentDO> commentDOS = commentRepository.selectCommentByServiceBusinessId(serviceBusinessId);
        List<CommentDTO> result = new ArrayList<>();
        for (CommentDO commentDO : commentDOS) {
            Result<SimpleUserDTO> simpleUserResult = userClient.fetchSimpleUserInfo(commentDO.getAuthor());
            SimpleUserDTO simpleUserDTO = ResultUtil.parsingData(simpleUserResult);
            CommentDTO coverDTO = new CommentDTO();
            coverDTO.setCommentId(commentDO.getCommentId());
            coverDTO.setAuthorId(simpleUserDTO.getUserId());
            coverDTO.setAuthorNickname(simpleUserDTO.getNickname());
            coverDTO.setAuthorAvatar(simpleUserDTO.getAvatar());
            coverDTO.setContent(commentDO.getContent());
            coverDTO.setTime(TimeUtil.calTimeDuration(commentDO.getCreateTime()));
            List<ReplyDTO> replyDTOS = replyService.obtainReplyByComment(commentDO.getCommentId());
            coverDTO.setReplies(replyDTOS);
            coverDTO.setReplyNum(replyDTOS.size());
            result.add(coverDTO);
        }
        return result;
    }

    @Override
    public int obtainCommentCount(Long serviceBusinessId) {
        return commentRepository.selectCommentCount(serviceBusinessId);
    }
}
