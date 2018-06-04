package com.uton.carsokApi.controller.response;

import com.uton.carsokApi.model.CarsokRecord;
import com.uton.carsokApi.model.PowerOfParentChild;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangdi on 2017/11/9.
 * desc:
 */
@Data
@Log4j
public class CarsOkCustmoerResponse {

    private Integer id;

    private String mobile;

    private String name;

    private String sex;

    private Date birthday;

    private String source;

    private Integer age;

    private String address;

    private String job;

    private String remark;

    private Date inTime;

    private Date outTime;

    private Integer inNumber;

    private String level;

    private String failReason;

    private String budget;

    private String isDrivingTest;

    private String area;

    private String province;

    private String city;

    private String district;

    private List<CarName> carName;

    private List<String> power;

    private Integer taskId;

    private String childAccountName;

    private List<Object> markerCar;

    private String customerTitle;

}  