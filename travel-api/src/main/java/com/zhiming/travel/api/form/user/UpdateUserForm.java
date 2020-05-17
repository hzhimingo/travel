package com.zhiming.travel.api.form.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author HuangZhiMing
 */
@Data
public class UpdateUserForm {
    @NotNull
    private Long userId;
    private String nickname;
    private LocalDate birthday;
    private String introduction;
    private Integer gender;
}
