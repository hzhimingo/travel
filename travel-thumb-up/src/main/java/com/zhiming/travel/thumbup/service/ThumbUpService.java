package com.zhiming.travel.thumbup.service;

public interface ThumbUpService {
    void thumbUp(Long userId, Long serviceBusinessId);
    void cancelThumbUp(Long userId, Long serviceBusinessId);
    int obtainThumbUpNum(Long serviceBusinessId);
    Boolean isThumbUp(Long userId, Long serviceBusinessId);
}
