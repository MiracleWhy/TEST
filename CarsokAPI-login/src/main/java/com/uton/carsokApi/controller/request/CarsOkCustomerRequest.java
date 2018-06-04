package com.uton.carsokApi.controller.request;

import com.uton.carsokApi.controller.response.CarName;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangdi on 2017/11/9.
 * desc:
 */
@Data
@Log4j
public class CarsOkCustomerRequest {

    private Integer id;
    //@Pattern(regexp = "^((13[0-9])|(14[5|7])|(15[0-9])|(18[0-9]))\\d{8}$",message = "手机号格式不正确！")
    private String mobile;
    @Size(min = 1,max = 50 ,message = "姓名长度不正确！")
    private String name;

    private String sex;

    private Date birthday;

    private String source;

    private Integer age;

    private String address;

    private String job;

    private String remark;
    @NotNull(message = "进店时间不能为空！")
    private Date inTime;


    private Date outTime;

    private Integer inNumber;
    @NotEmpty(message = "客户级别不能为空！")
    private String level;

    private String failReason;

    private String budget;

    private String isDrivingTest;

    private String area;

    private List<CarName> carName;

    private Integer accountId;

    private Integer childId;

    private String province;

    private String city;

    private String district;

    private String markerCar;
}  