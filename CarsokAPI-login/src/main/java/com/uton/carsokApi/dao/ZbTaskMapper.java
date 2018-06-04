package com.uton.carsokApi.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.uton.carsokApi.model.*;
import org.apache.ibatis.annotations.Param;

public interface ZbTaskMapper {
	int createTask(ZbTask zbtask);
	int createSxyTask(ZbTasksxy zbtasksxy);
	int createPgsTask(ZbTaskpgs zbtaskpgs);
	int updateByModel(ZbTask zbtask);
	int updateEnableFlag(int id);
	int insertMoneyInfo(ZbMoneyInfo moneyInfo);
	int createZbyTask(ZbTaskzby zbtaskzby);
	int createManagerTask(ZbTaskManager managertask);
	int insertBill(TaskZbBill taskZbBill);
	int deleteZbyBill(@Param("tid") int tid);
	List<TaskZbBill> selectBillListByTid(@Param("tid") int tid);

	List<ZbTask> selectByModel(ZbTask zbtask);
	List<ZbTask> selecthasDoTask(ZbTask zbtask);
	ZbTasksxy selectsxyTask(ZbTasksxy sxy);
	ZbTaskpgs selectpgsTask(ZbTaskpgs pgs);
	ZbTaskzby selectzbyTask(ZbTaskzby zby);
	ZbTaskManager selectmanagerTask(ZbTaskManager manager);
	List<ZbMoneyInfo> selectzbMoneyInfo(ZbMoneyInfo minfo);
	/**
	 * 修改sxy详情
	 */
	int updateSxyTask(ZbTasksxy sxy);
	/**
	 * 修改zby详情
	 */
	int updateZbyTask(ZbTaskzby zby);
	/**
	 * 修改manager详情
	 */
	int updateManagerTask(ZbTaskManager manager);

	/**
	 * 修改zbymoneyinfo表
	 */
	int updateZbyMoneyInfo(ZbMoneyInfo zbMoneyInfo);

	/**
	 * 通过id 删除表
	 */
	int deleteMoneyInfo(@Param("zbyId")int zbyId);

	/**
	 * 修改task表
	 */
	int updateTaskModel(ZbTask zbtask);

	/**
	 * 查询整备总数
	 * @param mobile
	 * @param roleList
	 * @return
	 */
	int selectZbCount(@Param("mobile")String mobile,@Param("roleList")List<String> roleList);

	/**
	 * 总经理查看整备数
	 * @param mobiles
	 * @param roleList
	 * @return
	 */
	int selectZbCountByZjl(@Param("mobiles")List<String> mobiles,@Param("roleList")List<String> roleList);
	/**
	 * 手续员代办
	 */
	List<CarsokZbTaskSxyWb> sxydb(Acount_child_Id acountChildId);
	/**
	 * 改变标志为不可用
	 */
	int updateEnable(int id);

	/**
	 * 将task表中的id 插入到 收车表中,以达到形成关联的目的
	 */
	int updateId(@Param("id") int id,@Param("acquisitioncarId") int acquisitioncarId);

	/**
	 * 将task表中的id 插入到手续员未办,以达到形成关联的目的
	 */
	int updateWBtaskId(@Param("id") int id,@Param("acquisitioncarId") int acquisitioncarId);

	/**
	 * 查询手续员待办数量
	 */
	int sxydbCount(Acount_child_Id acount_child_id);

	/**
	 * 查询手续员未办单号
	 */
	String selectSxyDh(int id);
	/**
	 * 根据tid字段查询图片路径
	 * @param taskid
	 * @return
	 */
	Map<String, String> selectPictureLook(@Param("taskid")int taskid);

	/**
	 * 根据tid查询数据
	 */
	int selectPgsByTid(@Param("tid") int tid);
	int selectZbyByTid(@Param("tid") int tid);
	int selectManagerByTid(@Param("tid") int tid);

	/**
	 *
	 */
	String selectAcarIdByTaskId(@Param("taskId")int taskId);

	void modifyAcquisitionClosingPrice(@Param("acarId")String acarId, @Param("closingPrice")String closingPrice);

	public BigDecimal selectClosingPriceByTaskId(@Param("taskid")int taskid);

	public Integer selectID(@Param("acquisitioncarId") int acquisitioncarId);

	List<ZbMoneyInfo> selectzbMoneyInfoAgain(@Param("zbyId") int zbyId,@Param("againTimes") int againTimes);

	List<TaskZbBill> selectBillListByTidAgain(@Param("tid") int tid,@Param("againTimes") int againTimes);
}
