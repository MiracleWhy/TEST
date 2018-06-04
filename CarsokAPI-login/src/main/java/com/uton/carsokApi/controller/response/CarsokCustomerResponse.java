package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.model.CarsokCustomer;
import com.uton.carsokApi.model.CarsokCustomerIntentionCar;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2017/11/14.
 */
@Data
public class CarsokCustomerResponse extends CarsokCustomer {
    private int flowCount;
    private List<CarsokCustomerIntentionCar> carLists;
    private int taskId;
    private String taskType;
    private String childAccountName;

}
