package com.zhiming.travel.common.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtil {
    public static String calTimeDuration(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(time, now);
        if (duration.toMillis() < 6000) {
            return "刚刚";
        }
        if (duration.toMinutes() < 60) {
            return duration.toMinutes() + "分钟前";
        }
        if (duration.toHours() < 24) {
            return duration.toHours() + "小时前";
        }
        return time.toLocalDate().toString();
    }
}
