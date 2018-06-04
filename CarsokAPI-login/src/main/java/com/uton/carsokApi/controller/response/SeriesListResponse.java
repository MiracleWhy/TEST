package com.uton.carsokApi.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class SeriesListResponse implements Serializable {


    private List<SeriesListBean> seriesList;

    public SeriesListResponse(List<SeriesListBean> seriesList){
        this.seriesList=seriesList;
    }
}
