package com.zhiming.travel.collect.service;

import com.zhiming.travel.api.form.collect.PostCollectForm;

public interface CollectService {
    void collect(PostCollectForm form);
    void cancelCollect(Long userId, Long serviceBusinessId);
    int obtainCollectCount(Long serviceBusinessId);
    Boolean isCollect(Long userId, Long serviceBusinessId);
}
