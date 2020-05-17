package com.zhiming.travel.qa.service.impl;

import com.zhiming.travel.api.client.CollectClient;
import com.zhiming.travel.api.client.UserClient;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.qa.AnswerInnerDTO;
import com.zhiming.travel.api.dto.qa.QuestionCoverDTO;
import com.zhiming.travel.api.dto.qa.QuestionDetailDTO;
import com.zhiming.travel.api.dto.user.SimpleUserDTO;
import com.zhiming.travel.api.form.qa.PostQuestionForm;
import com.zhiming.travel.api.query.qa.QuestionCoverQuery;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.IdGenerator;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.qa.domain.QuestionDO;
import com.zhiming.travel.qa.repository.QuestionRepository;
import com.zhiming.travel.qa.service.AnswerService;
import com.zhiming.travel.qa.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Value("${travel.service.serviceId}")
    private Long serviceId;
    @Value("${travel.service.machineId}")
    private Long machineId;
    private final CollectClient collectClient;
    private final UserClient userClient;
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    @Autowired
    public QuestionServiceImpl(
            CollectClient collectClient, UserClient userClient,
            QuestionRepository questionRepository,
            AnswerService answerService
    ) {
        this.collectClient = collectClient;
        this.userClient = userClient;
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    @Override
    public void postNewQuestion(PostQuestionForm form) {
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        Long questionId = idGenerator.nextId();
        System.out.println(questionId);
        QuestionDO record = new QuestionDO();
        BeanUtils.copyProperties(form, record);
        record.setAuthor(form.getUserId());
        record.setQuestionId(questionId);
        questionRepository.insert(record);
    }

    @Override
    public PageDTO obtainQuestionCovers(QuestionCoverQuery query) {
        QuestionDO record = new QuestionDO();
        BeanUtils.copyProperties(query, record);
        record.setStatus(0);
        record.setIsDelete(0);
        int boundary = query.getBoundary();
        int offset = query.getOffset() * 2;
        List<QuestionDO> questions = questionRepository.selectQuestionCovers(boundary, offset, record);
        PageDTO page = new PageDTO();
        page.setBoundary(boundary);
        page.setOffset(query.getOffset());
        if (questions.size() > query.getOffset()) {
            page.setHasNext(true);
            questions = questions.subList(0, query.getOffset());
        } else {
            page.setHasNext(false);
        }
        List<QuestionCoverDTO> results = new ArrayList<>();
        for (QuestionDO item : questions) {
            QuestionCoverDTO result = new QuestionCoverDTO();
            BeanUtils.copyProperties(item, result);
            AnswerInnerDTO answerInner = answerService.obtainAnswerInners(item.getQuestionId());
            result.setAnswer(answerInner);
            result.setAnswerNum(answerService.obtainAnswerCount(item.getQuestionId()));
            result.setFollowNum(0);
            result.setVisitedNum(0);
            results.add(result);
        }
        page.setData(results);
        return page;
    }

    @Override
    public QuestionDetailDTO obtainQuestionDetail(Long questionId, Long userId) {
        QuestionDO question = questionRepository.selectQuestionById(questionId);
        if (question == null) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        Result<SimpleUserDTO> userVO = userClient.fetchSimpleUserInfo(question.getAuthor());
        SimpleUserDTO simpleUserDTO = ResultUtil.parsingData(userVO);
        QuestionDetailDTO questionDetailDTO = new QuestionDetailDTO();
        BeanUtils.copyProperties(question, questionDetailDTO);
        questionDetailDTO.setAuthorId(simpleUserDTO.getUserId());
        questionDetailDTO.setAvatar(simpleUserDTO.getAvatar());
        questionDetailDTO.setNickname(simpleUserDTO.getNickname());
        questionDetailDTO.setDate(question.getCreateTime().toLocalDate());
        questionDetailDTO.setAnswerNum(answerService.obtainAnswerCount(questionId));
        Result<Boolean> isCollectResult = collectClient.isCollect(userId, questionId);
        Boolean isCollect = ResultUtil.parsingData(isCollectResult);
        questionDetailDTO.setIsCollect(isCollect);
        return questionDetailDTO;
    }
}
