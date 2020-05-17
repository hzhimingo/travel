package com.zhiming.travel.api.form.collect;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostCollectForm {
    @NotNull
    private Long userId;
    @NotNull
    private Long serviceBusinessId;
    @NotNull
    private Integer type;
}
