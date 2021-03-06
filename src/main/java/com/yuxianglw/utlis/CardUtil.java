package com.yuxianglw.utlis;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class CardUtil {
    /**
     * 根据身份证的号码算出当前身份证持有者的性别和年龄 18位身份证
     *
     * @return
     * @throws Exception
     */
    public static Integer getCarInfo(String CardCode){
        try {
            // 得到年份
            String year = CardCode.substring(6).substring(0, 4);
            // 得到月份
            String yue = CardCode.substring(10).substring(0, 2);
            // 得到当前的系统时间
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // 当前年份
            String fyear = format.format(date).substring(0, 4);
            // 月份
            String fyue = format.format(date).substring(5, 7);
            // String fday=format.format(date).substring(8,10);
            int age = 0;
            if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) {
                // 当前月份大于用户出身的月份表示已过生
                age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
                // 当前用户还没过生
            } else {
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            }
            return age;
        } catch (Exception e) {
           log.info("根据身份证查询年龄失败！");
           return 0;
        }
    }

}
