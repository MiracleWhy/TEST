package com.uton.carsokApi.service;

import com.uton.carsokApi.model.CarsokCustomerIntentionCar;

import java.util.List;

/**
 * Created by WANGYJ on 2017/11/8.
 */
public interface IObjectDiffExcutor<T> {
    //对象差分（根据数据表carsok_diffmap配置）
    String compareObjectWitTemplate(T oldObj, T newObj,int module);
    //list数据差分
    String compareListObject(List<T> oldList, List<T> newList, int module);
}
