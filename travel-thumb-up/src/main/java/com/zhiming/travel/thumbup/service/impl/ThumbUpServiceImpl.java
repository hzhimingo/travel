package com.zhiming.travel.thumbup.service.impl;

import com.zhiming.travel.thumbup.repository.ThumbUpRepository;
import com.zhiming.travel.thumbup.service.ThumbUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThumbUpServiceImpl implements ThumbUpService {

    private final ThumbUpRepository thumbUpRepository;

    @Autowired
    public ThumbUpServiceImpl(ThumbUpRepository thumbUpRepository) {
        this.thumbUpRepository = thumbUpRepository;
    }

    @Override
    public void thumbUp(Long userId, Long serviceBusinessId) {
        thumbUpRepository.insertNewThumbUp(userId, serviceBusinessId);
    }

    @Override
    public void cancelThumbUp(Long userId, Long serviceBusinessId) {
        thumbUpRepository.deleteThumbUp(userId, serviceBusinessId);
    }

    @Override
    public int obtainThumbUpNum(Long serviceBusinessId) {
        return thumbUpRepository.countThumbUpNum(serviceBusinessId);
    }

    @Override
    public Boolean isThumbUp(Long userId, Long serviceBusinessId) {
        int userThumbUpCount = thumbUpRepository.selectIsUserThumbUp(userId, serviceBusinessId);
        return userThumbUpCount != 0;
    }
}
