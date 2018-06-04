package com.uton.carsokApi.util;

import java.math.BigDecimal;

/**
 * Created by SEELE on 2018/1/16.
 */
public class CalculationUtil {

    /**
     * 运营报表环比计算统一方法
     * 环比=(当月-前月)÷前月
     *
     * @param current
     * @param previous
     * @return
     */
    public static double calculateChainMethod(double current, double previous) {
        if (current == 0 && previous == 0) {
            return 0;
        }
        if (current != 0 && previous == 0) {
            return 100;
        }
        if (current != 0 && previous != 0) {
            return new BigDecimal(current - previous)
                    .divide(new BigDecimal(previous), 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100))
                    .abs().doubleValue();
        }
        return 0;
    }

    /**
     * 运营报表箭头上下判断
     *
     * @param current
     * @param previous
     * @return
     */
    public static String judgeArrowMethod(double current, double previous) {
        if (current > previous) {
            return "up";
        }
        return "down";
    }

    /**
     * 运营报表 计算成交率
     *成交率=当月成交数量÷潜客数量
     * @param deal
     * @param total
     * @return
     */
    public static double getDealRate(double deal, int total) {
        if (total == 0 && deal == 0) {
            return 0;
        }
        if (total == 0 && deal != 0) {
            return 100;
        }
        if (total != 0 && deal != 0) {
            return new BigDecimal(deal)
                    .divide(new BigDecimal(total), 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100))
                    .doubleValue();
        }
        return 0;
    }
}
