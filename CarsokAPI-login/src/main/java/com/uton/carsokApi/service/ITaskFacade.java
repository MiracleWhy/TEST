package com.uton.carsokApi.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.uton.carsokApi.constants.enums.ModuleEnums;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.service.core.task.FilterSQLParam;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Created by WANGYJ on 2017/11/10.
 * 任务对外暴露接口
 */
public interface ITaskFacade {
    //创建任务，返回任务ID
    int createTask(TaskInitParam param);

    //底层查询接口
    //查询指定类型的任务
    PageInfo<CarsokTenureTask> queryTaskByStatusWithSQL(Integer page, Integer pageSize, TaskStatusEnums status, ModuleEnums module, FilterSQLParam sqlStatement);
    //查询任务
    PageInfo<CarsokTenureTask> queryTaskBySQLFilter(Integer page, Integer pageSize, FilterSQLParam sqlStatement);
    //通过EntityWrapper查询任务
    PageInfo<CarsokTenureTask> queryTaskByEntityWrapper(Integer page, Integer pageSize, EntityWrapper<CarsokTenureTask> entityWrapper);

    //任务操作类接口
    //完成任务
    Boolean finishTaskById(int taskId, String assignAccount, String childAccount);
    //跟新任务扩展数据
    Boolean updateExtraData(int taskId, Map<String,String > extraMap, String assignAccount, String childAccount);

    //任务删除
    Boolean deleteTaskById(int taskId, String assignAccount, String childAccount);
}