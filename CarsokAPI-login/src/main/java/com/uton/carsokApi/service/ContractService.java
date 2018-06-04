package com.uton.carsokApi.service;

import java.util.Date;
import java.util.List;

import com.uton.carsokApi.controller.response.CarContractResponse;
import com.uton.carsokApi.model.CarContract;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.uton.carsokApi.base.BaseResult;
import com.uton.carsokApi.controller.request.ContractRequest;
import com.uton.carsokApi.controller.response.ContractResponse;
import com.uton.carsokApi.dao.ContractMapper;
import com.uton.carsokApi.model.Contract;

@Service
public class ContractService {
	private final static Logger logger = Logger.getLogger(ContractService.class);
	
	@Autowired
	private ContractMapper contractMapper;
	
	
	public BaseResult querylist(ContractRequest vo) throws Exception {
		Contract contract = new Contract();
		contract.setCar_id(vo.getCarid());
		contract.setContract_type(vo.getContractType());
		List<Contract> list = contractMapper.querylist(contract);
		return BaseResult.success(list);
	}


	public BaseResult queryByVin(String vin) {
		List<Contract> list=contractMapper.selectByVin(vin);
		return BaseResult.success(list);
	}


	public BaseResult queryByCarId(String carId) {
		List<Contract> list=contractMapper.selectByCarId(carId);
		return BaseResult.success(list);
	}

    //根据车辆id查询合同
	public Page<ContractResponse> queryByCarIdListContract(List<String> carIdList) {
		Page<ContractResponse> pageCont=contractMapper.querylistContractType(carIdList);
		return pageCont;
	}

    public void saveCarContract(CarContract carContract) {
		Date date = new Date();
		carContract.setCreateTime(date);
		carContract.setUpdateTime(date);
		contractMapper.saveCarContract(carContract);
    }

	public BaseResult getCarContractList(CarContract vo) {
		BaseResult result = BaseResult.success();
		int startNum = vo.getPageNum() * vo.getPageSize();
		int size = vo.getPageSize();
		List<CarContract> list = contractMapper.getCarContractList(vo.getCarId(), startNum, size);
		result.setData(list);
		return result;
	}

	/**
	 * 根据id删除合同
	 * @param type
	 * @param id
	 * @return
	 */
	public Integer delContractById(String type,String id){

		if("1".equals(type)){//类型为1，为纸质合同，操作carsok_car_contract表
			return contractMapper.delPaperContract(id);
		}else if("2".equals(type)){//类型为2，为电子合同，操作carsok_contract表
			return contractMapper.delPdfContract(id);
		}else{
			return 0;
		}
	}

	public BaseResult carContractByCarId(String carId) {
		List<CarContract> list=contractMapper.carContractByCarId(carId);
		return BaseResult.success(list);
	}

	public BaseResult queryContractByCarIdAndType(String carId, String type) {
		List<Contract> contract = contractMapper.queryContractByCarIdAndType(carId, type);
		return BaseResult.success(contract);
	}
	public Page<ContractResponse> selectCarContractByCarId(List<String> carIdList, String accountId) {
		Page<ContractResponse> pageCont=contractMapper.selectCarContractByCarId(carIdList,accountId);
		return pageCont;
	}

	public List<CarContractResponse> selectCarList(List<String> carIdList) {
		return contractMapper.selectCarList(carIdList);
	}
}
