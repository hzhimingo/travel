package com.zhiming.travel.common.util;

import java.util.Random;

public class RandomCodeUtil {

    /**
     * @param digit 随机码的位数
     * @return String
     * 生成一个指定位数的随机码
     */
    public static String randomNumberCode(int digit) {
        if (digit <= 0) {
            throw new IllegalArgumentException();
        }
        int min = (int) Math.pow(10, digit - 1);
        int max = (int) Math.pow(10, digit) - 1;
        Random random = new Random();
        int randomNum = random.nextInt((max - min) + 1) + min;
        return String.valueOf(randomNum);
    }
}
