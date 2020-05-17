package com.zhiming.travel.moment.service;

import com.zhiming.travel.api.dto.moment.MomentDetailDTO;
import com.zhiming.travel.api.dto.page.PageDTO;
import com.zhiming.travel.api.form.moment.PostMomentForm;
import com.zhiming.travel.api.query.moment.MomentCoverQuery;

/**
 * @author HuangZhiMing
 */
public interface MomentService {
    PageDTO obtainMomentCovers(Long userId, MomentCoverQuery query);
    MomentDetailDTO obtainMomentDetail(Long momentId, Long userId);
    void addNewMoment(PostMomentForm form);
    void deleteMoment(Long momentId);
    void makeMomentEnable(Long momentId);
    void makeMomentDisable(Long momentId);
}
