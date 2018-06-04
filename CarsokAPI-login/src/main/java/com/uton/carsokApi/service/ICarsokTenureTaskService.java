package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.dao.CarsokTenureTaskMapper;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.service.core.task.FilterSQLParam;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangyj
 * @since 2017-11-09
 */
public interface ICarsokTenureTaskService extends IService<CarsokTenureTask> {
	//新建任务
	int createTenureTask(TaskInitParam param);
	//查询任务
	PageInfo<CarsokTenureTask> queryTasks(Integer page, Integer pageSize, FilterSQLParam sqlStatement);
	//查询任务
	PageInfo<CarsokTenureTask> queryTasksByEntityWrapper(Integer page, Integer pageSize, EntityWrapper<CarsokTenureTask> ew);
	//根据状态查询任务
	PageInfo<CarsokTenureTask> queryTasksByStatusAndExtraSQL(Integer page, Integer pageSize, TaskStatusEnums status, String module, FilterSQLParam extraSQL);
	//查询所有的ready状态的列表
	List<CarsokTenureTask> queryAllReadyTasks();

	/**
	* @author zhangdi
	* @date 2017/11/20 11:11
	* @Description: 潜客购车结束所有任务
	*/
	void finishTask(Integer cusId);
}