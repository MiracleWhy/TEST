package com.uton.carsokApi.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uton.carsokApi.controller.request.PosApplyRequest;
import com.uton.carsokApi.dao.PosApplyMapper;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.PosApply;
import com.uton.carsokApi.service.PosApplyService;

@Service
public class PosApplyServiceImpl implements PosApplyService {
	@Autowired
	private PosApplyMapper posApplyMapper;

	@Override
	public PosApply queryApplyInfo(String accountId) {
		return posApplyMapper.selectByAccountId(accountId);
	}

	@Override
	public OperateResult create(PosApply posApply) {
		posApply.setEnable(true);
		posApply.setGmtCreate(new Date());
		posApply.setGmtModify(new Date());
		posApplyMapper.insertSelective(posApply);
		return new OperateResult(true, "操作成功");
	}

	@Override
	public OperateResult modify(PosApply posApply) {
		posApply.setGmtModify(new Date());
		posApplyMapper.updateByPrimaryKeySelective(posApply);
		return new OperateResult(true, "操作成功");
	}

	@Override
	public OperateResult createApplyOrder(PosApplyRequest request) {
		return null;
	}

}
