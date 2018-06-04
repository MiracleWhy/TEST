package com.uton.carsokApi.constants;

public interface PosCode {
	  public static final String GetPosFail ="3001";
	  public static final String GetPosFailInfo ="未领取POS机";
	  
	  public static final String PosApplyChecking ="3002";
	  public static final String PosApplyCheckingInfo ="领取POS审核中";
	  
	  public static final String PosApplyCheckFail ="3003";
	  public static final String PosApplyCheckFailInfo ="POS机审核未通过";
	  
	  public static final String PosOpenFail ="3004";
	  public static final String PosOpenFailInfo ="POS机未开通";
}
