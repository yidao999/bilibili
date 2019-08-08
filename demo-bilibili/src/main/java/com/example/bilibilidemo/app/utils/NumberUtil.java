package com.example.bilibilidemo.app.utils;

/**
 * author: 小川
 * Date: 2019/8/2
 * Description:
 */
public class NumberUtil {
    public static String converString(int num) {
        if (num < 100000) {
            return String.valueOf(num);
        }
        String unit = "万";
        double newNum = num / 10000.0;
        String numStr = String.format("%." + 1 + "f", newNum);
        return numStr + unit;
    }
}
