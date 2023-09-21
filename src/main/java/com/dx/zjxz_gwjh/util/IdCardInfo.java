package com.dx.zjxz_gwjh.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdCardInfo {
    public static Date getBirthDate(String idCard) throws ParseException {
        String birthDateStr = idCard.substring(6, 14); // 提取出生日期部分
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.parse(birthDateStr);
    }

    public static String getGender(String idCard) {
        char genderCode = idCard.charAt(16); // 提取性别代码
        return (genderCode % 2 == 0) ? "女" : "男"; // 偶数表示“女”，奇数表示“男”
    }
}
