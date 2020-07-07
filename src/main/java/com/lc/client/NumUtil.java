package com.lc.client;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class NumUtil {

    private NumUtil() {
    }

    /**
     * Round to two decimal places
     *
     * @param number    value
     * @param maxDigits result format
     * @return
     */
    public static Double formatNumber(String number, Integer maxDigits) {
        BigDecimal bigDecimal = new BigDecimal(number);
        String amountHandleWay = "4";
        int round = BigDecimal.ROUND_HALF_DOWN;
        if (StringUtils.isNotEmpty(amountHandleWay)) {
            round = Integer.parseInt(amountHandleWay);
        }
        return bigDecimal.setScale(maxDigits, round).doubleValue();
    }

}
