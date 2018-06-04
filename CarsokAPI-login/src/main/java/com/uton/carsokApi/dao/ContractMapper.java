package com.uton.carsokApi.dao;

import java.util.List;
import java.util.Map;

import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.response.CarContractResponse;
import com.uton.carsokApi.model.CarContract;
import com.uton.carsokApi.model.ContractMsg;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.uton.carsokApi.controller.response.ContractResponse;
import com.uton.carsokApi.model.Contract;

public interface ContractMapper {
	List<Contract> querylist(Contract contract);
	
	List<Contract> queryByCar(Map map);

	List<Contract> selectByVin(String vin);

	List<Contract> selectByCarId(String carId);
    //根据车辆id查询合同类型
	Page<ContractResponse> querylistContractType(@Param("carIdList")List<String> carIdList);

    void saveCarContract(CarContract carContract);

	List<CarContract> getCarContractList(@Param("carId") int carId, @Param("startNum")int startNum, @Param("size")int size);

	/**
	 *根据id删除纸质合同
	 * @param id
	 * @return
	 */
	int delPaperContract(@Param("id") String id);

	/**
	 * 根据id删除pdf合同
	 * @param id
	 * @return
	 */
	int delPdfContract(@Param("id") String id);

	List<CarContract> carContractByCarId(String carId);

	List<Contract> queryContractByCarIdAndType(@Param("carId") String carId, @Param("type") String type);
	/**
	* @author zhangD
	* @date 2018/2/28 11:55
	* @Description: 插入合同
	*/
	void insertPdfContract(ContractMsg c);
	Page<ContractResponse> selectCarContractByCarId(@Param("carIdList") List<String> carIdList,@Param("accountId") String accountId);

	List<CarContractResponse> selectCarList(@Param("carIdList")List<String> carIdList);
}
