package com.zhiming.travel.topic.service.impl;

import com.zhiming.travel.api.client.PictureClient;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.dto.picture.PictureDTO;
import com.zhiming.travel.api.dto.topic.TopicCoverDTO;
import com.zhiming.travel.api.dto.topic.TopicDTO;
import com.zhiming.travel.api.form.topic.PostTopicForm;
import com.zhiming.travel.api.query.topic.TopicCoverQuery;
import com.zhiming.travel.common.enums.ResultEnum;
import com.zhiming.travel.common.exception.TravelServiceException;
import com.zhiming.travel.common.util.IdGenerator;
import com.zhiming.travel.common.util.ResultUtil;
import com.zhiming.travel.common.vo.Result;
import com.zhiming.travel.topic.domain.TopicDO;
import com.zhiming.travel.topic.repository.TopicRepository;
import com.zhiming.travel.topic.service.TopicService;
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
public class TopicServiceImpl implements TopicService {

    @Value("${travel.service.serviceId}")
    private Long serviceId;
    @Value("${travel.service.machineId}")
    private Long machineId;

    private final TopicRepository topicRepository;
    private final PictureClient pictureClient;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, PictureClient pictureClient) {
        this.pictureClient = pictureClient;
        this.topicRepository = topicRepository;
    }


    @Override
    public TopicDTO obtainTopicDetail(Long topicId) {
        TopicDO topicDO = topicRepository.selectTopicById(topicId);
        if (topicDO == null) {
            throw new TravelServiceException(ResultEnum.RESOURCE_NOT_FOUND);
        }
        TopicDTO result = new TopicDTO();
        BeanUtils.copyProperties(topicDO, result);
        return result;
    }

    @Override
    public PageDTO obtainTopicCovers(TopicCoverQuery query) {
        int boundary = query.getBoundary();
        int offset = query.getOffset() * 2;
        TopicDO record = new TopicDO();
        BeanUtils.copyProperties(query, record);
        List<TopicDO> topics = topicRepository.selectTopicCover(boundary, offset, record);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setBoundary(boundary);
        pageDTO.setOffset(query.getOffset());
        if (topics.size() > query.getOffset()) {
            pageDTO.setHasNext(true);
            topics = topics.subList(0, query.getOffset());
        } else {
            pageDTO.setHasNext(false);
        }
        List<TopicCoverDTO> results = new ArrayList<>();
        for (TopicDO item : topics) {
            TopicCoverDTO result = new TopicCoverDTO();
            BeanUtils.copyProperties(item, result);
            results.add(result);
        }
        pageDTO.setData(results);
        return pageDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postNewTopic(PostTopicForm form) {
        Result<PictureDTO> uploadVO = pictureClient.uploadSinglePicture(form.getPic());
        PictureDTO pictureDTO = ResultUtil.parsingData(uploadVO);
        String url = pictureDTO.getUrl();
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        Long topicId = idGenerator.nextId();
        TopicDO record = new TopicDO();
        BeanUtils.copyProperties(form, record);
        record.setBackground(url);
        record.setTopicId(topicId);
        topicRepository.insertNewTopic(record);
    }

    @Override
    public void deleteTopic(Long topicId) {
        int isTopicExist = topicRepository.isTopicExist(topicId);
        if (isTopicExist == 0) {
            throw new TravelServiceException(ResultEnum.SPOT_TYPE_NOT_EXIST);
        }
        topicRepository.deleteByPrimaryKey(topicId);
    }

    @Override
    public boolean isTopicsExist(Long[] topics) {
        int right = topicRepository.isTopicsExist(topics);
        return right == topics.length;
    }
}
