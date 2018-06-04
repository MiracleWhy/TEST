package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.model.Picture;

public interface PictureMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByPid(Integer id);

    int insertSelective(Picture record);

    List<Picture> selectByModel(Picture record);

    int updateByPrimaryKeySelective(Picture record);

}