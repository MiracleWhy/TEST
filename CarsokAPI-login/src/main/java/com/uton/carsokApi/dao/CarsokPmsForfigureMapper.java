package com.uton.carsokApi.dao;
import com.uton.carsokApi.model.CarsokPmsForfigure;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2017-10-10
 */
public interface CarsokPmsForfigureMapper {



    int insert(CarsokPmsForfigure carsokPmsForfigure);

//    CarsokPmsForfigure selectById(Integer id);

    int deleteById(String id);

}