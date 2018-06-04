package com.uton.carsokApi.controller.request;

import com.uton.carsokApi.model.GleefulReport;

import java.util.List;

/**
 * Created by SEELE on 2017/7/14.
 */
public class GleefulReportRequest extends GleefulReport {
    /**2.4.6版本以前**/
    private List<String> base64List;


    public List<String> getBase64List() {
        return base64List;
    }

    public void setBase64List(List<String> base64List) {
        this.base64List = base64List;
    }

}
