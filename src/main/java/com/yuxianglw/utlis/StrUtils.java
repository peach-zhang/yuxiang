package com.yuxianglw.utlis;

import com.yuxianglw.common.BizConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangtao
 */
public class StrUtils {

    //性别中英文转化
    public static String sxeTransformChinese(String str) {
        if (StringUtils.equals(str, BizConstant.MAN)) {
            return BizConstant.ZH_MAN;
        } else if (StringUtils.equals(str, BizConstant.WOMAN)) {
            return BizConstant.ZH_WOMAN;
        }
        return str;
    }

}
