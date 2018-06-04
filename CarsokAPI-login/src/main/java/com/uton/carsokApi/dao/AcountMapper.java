package com.uton.carsokApi.dao;

import java.util.List;

import com.uton.carsokApi.controller.response.CheckMobileExist;
import com.uton.carsokApi.model.Acount;
import com.uton.carsokApi.model.Acount_child_Id;
import com.uton.carsokApi.model.AddNextVisit;
import org.apache.ibatis.annotations.Param;

public interface AcountMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Acount record);

    Acount selectByPrimaryKey(Integer id);

    int updateBySelective(Acount record);

	Acount selectByModel(Acount record);
	
	Acount selectByMobile(String mobile);
	
	List<Acount> selectAll();

    int updateQuotientScore(Acount record);

    int updateToken(Acount record);

    Acount selectToken();

    Acount selectQuotientScore(Acount record);

	List<CheckMobileExist> querySubUserOnlyChk(String mobile);

    List<String> selectAllAccount();

    List<Acount> selectByOpenId(String openId);

    /**
     * 通过inviter查询
     * @param inviter
     * @return
     */
    List<Acount> selectExtention(@Param("inviter") String inviter);


    int insertContent(@Param("title") String title,@Param("content") String content,@Param("pushTo")String pushTo,@Param("contentType")String contentType,@Param("push_status")int pushStatus);

    String telephone(@Param("id") String id);

    Integer updateAccountForApplyPro(Acount acount);
    Integer queryApplyStatus(Integer id);
    List selectQx(Integer acountChildId);

    int updateMerchantInfo(Acount acount);

    List<Acount> selectSimLogin();
    List<Acount> selectSimLogin1();
    List<Acount> selectSimLogin2();
    List<Acount> selectSimLogin3();

    Acount selectByAccountCode(@Param("accountCode") String accountCode);
}