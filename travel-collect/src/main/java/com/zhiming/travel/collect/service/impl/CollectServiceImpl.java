package com.zhiming.travel.collect.service.impl;

import com.zhiming.travel.api.form.collect.PostCollectForm;
import com.zhiming.travel.collect.domain.CollectDO;
import com.zhiming.travel.collect.repository.CollectRepository;
import com.zhiming.travel.collect.service.CollectService;
import com.zhiming.travel.common.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

@Service
public class CollectServiceImpl implements CollectService {

    @Value("${travel.service.serviceId}")
    private Long serviceId;
    @Value("${travel.service.machineId}")
    private Long machineId;

    private final CollectRepository collectRepository;

    @Autowired
    public CollectServiceImpl(CollectRepository collectRepository) {
        this.collectRepository = collectRepository;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void collect(PostCollectForm form) {
        CollectDO record = new CollectDO();
        IdGenerator idGenerator = new IdGenerator(serviceId, machineId);
        Long collectId = idGenerator.nextId();
        record.setCollectId(collectId);
        record.setCollectUser(form.getUserId());
        record.setType(form.getType());
        record.setServiceBusinessId(form.getServiceBusinessId());
        collectRepository.insertCollect(record);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void cancelCollect(Long userId, Long serviceBusinessId) {
        collectRepository.deleteCollect(userId, serviceBusinessId);
    }

    @Override
    public int obtainCollectCount(Long serviceBusinessId) {
        return collectRepository.selectCountCollect(serviceBusinessId);
    }

    @Override
    public Boolean isCollect(Long userId, Long serviceBusinessId) {
        int isCollect = collectRepository.selectIsCollect(userId, serviceBusinessId);
        return isCollect != 0;
    }
}
