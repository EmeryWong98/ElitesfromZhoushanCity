package com.dx.zjxz_gwjh.util;

import java.util.Calendar;
import java.util.Date;

public class Format {
    /**
     * 将年份转换为Date时间，默认为首月1号
     * @param year 年份
     * @return Date
     */
    public Date YearStringToYearDate(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY); // 设置月份为一月
        calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置日期为一号
        return calendar.getTime();
    }
}
