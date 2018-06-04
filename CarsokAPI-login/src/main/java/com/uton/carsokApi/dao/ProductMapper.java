package com.uton.carsokApi.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.uton.carsokApi.model.CarsokChildAccount;
import com.uton.carsokApi.model.ChildAccount;
import com.uton.carsokApi.model.QueryPublishCount;
import com.uton.carsokApi.model.Product;
import com.utonw.searchcenter.api.entity.StoreCar;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Product record);

    Product selectByModel(Product record);
    
    List<Product> selectByPage(Map map);

    int selectCount(Map map);

    int updateByModel(Product record);
    
    List<Product> selectOnsaleCount(Map map);
    
    List<Product> selectByName(Map map);
    
    int selectNewCount(int accountId);
     
    int selectSaleCount(int accountId);

	Map selectCountByStatus(Map map);
    Map selectCountByStatusForQianke(Map map);

    List<Map> queryPutAwayCountByDay(List list);

    List<Map> querySoldCountByDay(List list);

    List<Map> queryPutAwayCountByWeek(List list);

    List<Map> querySoldCountByWeek(List list);

    List<Map> queryPutAwayCountByMonth(List list);

    List<Map> querySoldCountByMonth(List list);

    Integer queryRepertoryCount(List list);
    
	Product selectByIdForUpdate(String productId, Integer id);

	Product selectByProductId(String productId);

    int updateReserve(@Param("id") int id, @Param("reserveIf") int reserveIf);

    BigDecimal getAcqPriceByVin(@Param("vin") String vin);

    Product selectPidByVin(@Param("vin") String vin,@Param("accountId") int accountId,@Param("isSale")boolean isSale);

    StoreCar SelectIndexById(@Param("productId")Integer productId);

    List<StoreCar> SelectIndexByIds(@Param("ids") String[] ids);
    /**
     * 是否申请消费贷
     */
    int selectApplyCSD(Integer id);

    List<Product> queryCarDetailByStatusAndDate(@Param("accountId")Integer accountId,@Param("status")Integer status,@Param("date")String date,@Param("type")String type);

    /**今日战报：本日已售辆车**/
    Integer querySaleCountByDay(@Param("accountId")Integer accountId
            , @Param("childAccounts") List<CarsokChildAccount> childAccounts
            , @Param("date")Date date
            , @Param("type")String type);

    /**
     * 查询已售车辆
     * @param accountId
     * @return
     */
    int selectSaledCount(@Param("accountId")Integer accountId);

    /**
     * 查询在售车辆
     * @param accountId
     * @return
     */
    int selectOnSaleCounts(@Param("accountId")Integer accountId);

    /**
     * 查询累计销售额
     * @param accountId
     * @return
     */
    BigDecimal selectSaledPriceCount(@Param("accountId")Integer accountId);
}