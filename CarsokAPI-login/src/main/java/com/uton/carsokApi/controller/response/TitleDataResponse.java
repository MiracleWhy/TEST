package com.uton.carsokApi.controller.response;

import lombok.Data;

/**
 * Created by zhangdi on 2018/1/24.
 * desc:
 */
@Data
public class TitleDataResponse {

    /**
     * 今日新增
     */
    private int todayNew;
    /**
     * 累计新增
     */
    private int grandTotalNew;
    /**
     * 今日浏览
     */
    private int todayBrowse;
    /**
     * 累计浏览
     */
    private int grandTotalBrowse;

}  