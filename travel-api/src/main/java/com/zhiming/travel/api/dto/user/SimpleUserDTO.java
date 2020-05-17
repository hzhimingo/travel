package com.zhiming.travel.api.dto.user;

import lombok.Data;

@Data
public class SimpleUserDTO {
    private Long userId;
    private String nickname;
    private String avatar;
}
