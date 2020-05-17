package com.zhiming.travel.cr.service.impl;

import com.zhiming.travel.api.client.UserClient;
import com.zhiming.travel.api.dto.cr.CommentCoverDTO;
import com.zhiming.travel.api.dto.cr.CommentDTO;
import com.zhiming.travel.api.dto.cr.ReplyDTO;
import com.zhiming.travel.api.dto.user.SimpleUserDTO;
import com.zhiming.travel.api.form.cr.PostReplyForm;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.IdGenerator;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.util.TimeUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.common.vo.ResultVO;
import com.zhiming.travel.cr.domain.CommentDO;
import com.zhiming.travel.cr.domain.ReplyDO;
import com.zhiming.travel.cr.repository.ReplyRepository;
import com.zhiming.travel.cr.service.ReplyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Value("${travel.service.serviceId}")
    private Long serviceId;
    @Value("${travel.service.machineId}")
    private Long machineId;

    private final UserClient userClient;
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyServiceImpl(UserClient userClient, ReplyRepository replyRepository) {
        this.userClient = userClient;
        this.replyRepository = replyRepository;
    }

    @Override
    public void postNewReply(PostReplyForm form) {
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        Long replyId = idGenerator.nextId();
        ReplyDO record = new ReplyDO();
        BeanUtils.copyProperties(form, record);
        record.setAuthor(form.getUserId());
        record.setComment(form.getCommentId());
        record.setReplyId(replyId);
        replyRepository.insertNewReply(record);
    }

    @Override
    public List<ReplyDTO> obtainReplyByComment(Long commentId) {
        List<ReplyDO> replyDOS = replyRepository.selectReplyByCommentId(commentId);
        List<ReplyDTO> result = new ArrayList<>();
        for (ReplyDO replyDO : replyDOS) {
            Result<SimpleUserDTO> authorResult = userClient.fetchSimpleUserInfo(replyDO.getAuthor());
            Result<SimpleUserDTO> replyToResult = userClient.fetchSimpleUserInfo(replyDO.getReplyTo());
            SimpleUserDTO author = ResultUtil.parsingData(authorResult);
            SimpleUserDTO replyTo = ResultUtil.parsingData(replyToResult);
            ReplyDTO coverDTO = new ReplyDTO();
            coverDTO.setAuthorId(replyDO.getAuthor());
            coverDTO.setAuthorNickname(author.getNickname());
            coverDTO.setAuthorAvatar(author.getAvatar());
            coverDTO.setReplyToId(replyDO.getReplyTo());
            coverDTO.setReplyToNickname(replyTo.getNickname());
            coverDTO.setReplyToAvatar(replyTo.getAvatar());
            coverDTO.setContent(replyDO.getContent());
            coverDTO.setTime(TimeUtil.calTimeDuration(replyDO.getCreateTime()));
            result.add(coverDTO);
        }
        return result;
    }
}
