package com.uton.carsokApi.event.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.uton.carsokApi.dao.AccountLklMapper;
import com.uton.carsokApi.event.anno.EventHandler;
import com.uton.carsokApi.event.constants.EventConstants;
import com.uton.carsokApi.event.constants.EventHandle;
import com.uton.carsokApi.event.constants.OperateResult;
import com.uton.carsokApi.model.AccountLkl;
import com.uton.carsokApi.model.BaseEvent;
import com.uton.carsokApi.pay.lakala.model.LaKaLaApplyStatusEnum;
import com.uton.carsokApi.pay.lakala.util.LaKaLaConstants;

@EventHandler(name = EventConstants.LAKALA_ACOUNT_APPLY_NOTIFY_EVENT)
@Service
public class LaKaLaApplyNotifyEventHandler extends EventHandle {
	@Autowired
	private AccountLklMapper accountLklMapper;


	/**
	 * 拉卡拉开户通知处理
	 */
	@Override
	public OperateResult handle(BaseEvent event) {
		JSONObject data = JSONObject.parseObject(event.getData());
		String accountId = data.getString("partnerUserId");
		AccountLkl accountLkl = accountLklMapper.selectByAccountIdForUpdate(accountId);
		if (accountLkl == null) {
			accountLkl = new AccountLkl();
			accountLkl.setAccountId(accountId);
			accountLkl.setGmtModify(new Date());
			accountLkl.setAccountNo(data.getString("accountNo"));
			accountLkl.setAccountBankName(data.getString("accountBankName"));
			accountLkl.setGmtCreate(new Date());
			accountLkl.setAddress(data.getString("address"));
			accountLkl.setAreaName(data.getString("areaName"));
			accountLkl.setMerName(data.getString("merName"));
			accountLkl.setOpenStatus(LaKaLaConstants.FAIL);
			accountLkl.setApplyStatus(LaKaLaApplyStatusEnum.WAIT_TRADE_TEST);
			accountLkl.setMemo("LKL");
			accountLkl.setRealName(data.getString("realName"));
			accountLkl.setGmtOpen(new Date());
			accountLklMapper.insertSelective(accountLkl);
			// 进行消息推送
			// 进行车商分添加
			return new OperateResult(true, null);
		} else {
			// 更新账户信息
			accountLkl.setAccountId(accountId);
			accountLkl.setGmtModify(new Date());
			accountLkl.setAccountNo(data.getString("accountNo"));
			accountLkl.setAccountBankName(data.getString("accountBankName"));
			accountLkl.setGmtCreate(new Date());
			accountLkl.setAddress(data.getString("address"));
			accountLkl.setAreaName(data.getString("areaName"));
			accountLkl.setMerName(data.getString("merName"));
			accountLkl.setOpenStatus(LaKaLaConstants.SUCCESS);
			accountLkl.setMemo("LKL");
			accountLkl.setRealName(data.getString("realName"));
			accountLkl.setGmtOpen(new Date());
			accountLklMapper.updateByPrimaryKeySelective(accountLkl);
			return new OperateResult(true, null);
		}

	}

}
