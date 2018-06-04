package com.uton.carsokApi.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.constants.enums.TaskTypeEnums;
import com.uton.carsokApi.dao.CarsokTenureTaskMapper;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.model.CarsokCustomer;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.model.Task;
import com.uton.carsokApi.service.ICarsokTenureTaskService;
import com.uton.carsokApi.service.core.task.FilterSQLParam;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.AAShapePipe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author wangyj
 * @since 2017-11-09
 */
@Service("taskservice")
public class CarsokTenureTaskServiceImpl extends ServiceImpl<CarsokTenureTaskMapper, CarsokTenureTask> implements ICarsokTenureTaskService {
    Logger logger = Logger.getLogger(CarsokTenureTaskServiceImpl.class);

    @Autowired
    private CarsokTenureTaskMapper carsokTenureTaskMapper;

    /**
     * @Description: 创建任务
     * @Param: param参数
     * @Return:
     * @author WANGYJ
     * @date 2017/11/11 13:40
     */
    @Override
    public int createTenureTask(TaskInitParam param) {
        CarsokTenureTask carsokTenureTask = new CarsokTenureTask();
        carsokTenureTask.setBusinessId(param.getBusiness_id());
        Date curDate = new Date();
        carsokTenureTask.setCreateTime(curDate);
        carsokTenureTask.setEnable(1);
        carsokTenureTask.setUpdateTime(curDate);
        if (param.getScheduled_time() != null){
            carsokTenureTask.setScheduledTime(param.getScheduled_time());
        }
        if (param.getTask_execute_group()!=null){
            carsokTenureTask.setTaskExecuteGroup(param.getTask_execute_group());
        }
        carsokTenureTask.setType(param.getType().name());
        //如果未设置默认为ready状态，如果设置了按照设置的类型修改
        if (param.getStatusEnums() == null){
            carsokTenureTask.setTaskStatus(TaskStatusEnums.ready.name());
        }else {
            carsokTenureTask.setTaskStatus(param.getStatusEnums().name());
        }
        //设定模块
        carsokTenureTask.setModule(param.getModule().name());
        //乐观锁初始版本值
        carsokTenureTask.setVersion(1);
        JSONObject jsonObject = JSONObject.fromObject(param.getExtraFields());
        carsokTenureTask.setExtraFields(jsonObject.toString());
        int result = carsokTenureTaskMapper.insert(carsokTenureTask);
        if (result<=0){
            JSONObject logobject = JSONObject.fromObject(carsokTenureTask);
            logger.error("任务插入失败:"+logobject.toString());
            return -1;
        }else{
            JSONObject logobject = JSONObject.fromObject(carsokTenureTask);
            logger.debug("任务插入成功"+logobject.toString());
            return carsokTenureTask.getId();
        }
    }

    /**
     * @Description: 自定义查询
     * @Param:
     * @Return:
     * @author WANGYJ
     * @date 2017/11/11 14:08
     */
    @Override
    public PageInfo<CarsokTenureTask> queryTasks(Integer page, Integer pageSize, FilterSQLParam sqlStatement) {
        Wrapper<CarsokTenureTask> ew = new EntityWrapper<CarsokTenureTask>().addFilter(sqlStatement.getSqlTemplate());
        if (sqlStatement.getOrderByColumn()!=null){
            ew = ew.orderBy(sqlStatement.getOrderByColumn(),sqlStatement.getIsAsc());
        }
        PageHelper.startPage(page,pageSize);
        List<CarsokTenureTask> list = carsokTenureTaskMapper.queryTaskBySqlStatement(ew);
        return new PageInfo<>(list);

    }

    @Override
    public PageInfo<CarsokTenureTask> queryTasksByEntityWrapper(Integer page, Integer pageSize, EntityWrapper<CarsokTenureTask> ew) {
        PageHelper.startPage(page,pageSize);
        List<CarsokTenureTask> list = carsokTenureTaskMapper.queryTaskBySqlStatement(ew);
        return new PageInfo<>(list);
    }

    /**
     * @Description: 查询指定状态的待办列表
     * @Param:
     * @Return: 待办列表
     * @author WANGYJ
     * @date 2017/11/13 0:31
     */
    @Override
    public PageInfo<CarsokTenureTask>  queryTasksByStatusAndExtraSQL(Integer page, Integer pageSize, TaskStatusEnums status, String module,FilterSQLParam extraSQL) {
        PageInfo<CarsokTenureTask> pageData = null;
        switch (status){
            case delay:
                pageData = queryDelayTasksByExtraSQL(page, pageSize, module, extraSQL);
                break;
            case ready:
                pageData = queryReadyTasksByExtraSQL(page,pageSize, module, extraSQL);
                break;
            case finish:
                pageData = queryFinishTasksByExtraSQL(page, pageSize, module, extraSQL);
                break;
            case terminate:
                pageData = queryTermateTasksByExtraSQL(page, pageSize, module, extraSQL);
                break;
            default:
                return pageData;

        }
        return pageData;
    }

    /**
     * @Description: 查询所有的ready状态的
     * @Param:
     * @Return:
     * @author WANGYJ
     * @date 2017/11/13 21:45
     */
    @Override
    public List<CarsokTenureTask> queryAllReadyTasks() {
        Wrapper<CarsokTenureTask> ew = new EntityWrapper<CarsokTenureTask>().eq("task_status",TaskStatusEnums.ready.name())
                .eq("enable",1);
        List<CarsokTenureTask> list = carsokTenureTaskMapper.queryTaskBySqlStatement(ew);
        return list;
    }

    /**
     * @Description: 查询延期状态的任务
     * @Param:
     * @Return:
     * @author WANGYJ
     * @date 2017/11/13 0:34
     */
    private PageInfo<CarsokTenureTask> queryDelayTasksByExtraSQL(Integer page, Integer pageSize, String module, FilterSQLParam extraSQL){
        Wrapper<CarsokTenureTask> ew = new EntityWrapper<CarsokTenureTask>().eq("task_status","delay")
                .eq("enable",1)
                .eq("module",module);
        if (extraSQL!=null){
            if (extraSQL.getSqlTemplate()!=null){
                ew = ew.addFilter(extraSQL.getSqlTemplate());
            }
            if (extraSQL.getOrderByColumn()!=null){
                ew = ew.orderBy(extraSQL.getOrderByColumn(),extraSQL.getIsAsc());
            }
        }
        PageHelper.startPage(page,pageSize);
        List<CarsokTenureTask> list = carsokTenureTaskMapper.queryTaskBySqlStatement(ew);
        return new PageInfo<>(list);
    }
    /**
     * @Description: 查询本日待办状态的任务
     * @Param:
     * @Return:
     * @author WANGYJ
     * @date 2017/11/13 0:34
     */
    private PageInfo<CarsokTenureTask> queryReadyTasksByExtraSQL(Integer page, Integer pageSize, String module,FilterSQLParam extraSQL) {
        Date curDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfdetail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String day = sdf.format(curDate);
        String startday = day.concat(" 00:00:00");
        Date startDate = null;
        try {
            startDate = sdfdetail.parse(startday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String endday = day.concat(" 23:59:59");
        Date endDate = null;
        try {
            endDate = sdfdetail.parse(endday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Wrapper<CarsokTenureTask> ew = new EntityWrapper<CarsokTenureTask>().eq("task_status","ready")
                .eq("enable",1)
                .eq("module",module)
                .between("scheduled_time",startDate,endDate);
        if (extraSQL!=null){
            if (extraSQL.getSqlTemplate()!=null){
                ew = ew.addFilter(extraSQL.getSqlTemplate());
            }
            if (extraSQL.getOrderByColumn()!=null){
                ew = ew.orderBy(extraSQL.getOrderByColumn(),extraSQL.getIsAsc());
            }
        }
        PageHelper.startPage(page,pageSize);
        List<CarsokTenureTask> list = carsokTenureTaskMapper.queryTaskBySqlStatement(ew);
        return new PageInfo<>(list);
    }

    /**
     * @Description: 查询本日完成任务
     * @Param:
     * @Return:
     * @author WANGYJ
     * @date 2017/11/13 7:46
     */
    private PageInfo<CarsokTenureTask> queryFinishTasksByExtraSQL(Integer page, Integer pageSize, String module, FilterSQLParam extraSQL){
        Date curDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfdetail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String day = sdf.format(curDate);
        String startday = day.concat(" 00:00:00");
        Date startDate = null;
        try {
            startDate = sdfdetail.parse(startday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String endday = day.concat(" 23:59:59");
        Date endDate = null;
        try {
            endDate = sdfdetail.parse(endday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Wrapper<CarsokTenureTask> ew = new EntityWrapper<CarsokTenureTask>().eq("task_status","finish")
                .eq("enable",1)
                .eq("module",module)
                .between("actual_finish_time",startDate,endDate);
        if (extraSQL!=null){
            if (extraSQL.getSqlTemplate()!=null){
                ew = ew.addFilter(extraSQL.getSqlTemplate());
            }
            if (extraSQL.getOrderByColumn()!=null){
                ew = ew.orderBy(extraSQL.getOrderByColumn(),extraSQL.getIsAsc());
            }
        }
        PageHelper.startPage(page,pageSize);
        List<CarsokTenureTask> list = carsokTenureTaskMapper.queryTaskBySqlStatement(ew);
        return new PageInfo<>(list);
    }

    /**
     * @Description: 查询终止任务
     * @Param:
     * @Return:
     * @author WANGYJ
     * @date 2017/11/13 7:50
     */
    private PageInfo<CarsokTenureTask> queryTermateTasksByExtraSQL(Integer page, Integer pageSize, String module, FilterSQLParam extraSQL){
        Wrapper<CarsokTenureTask> ew = new EntityWrapper<CarsokTenureTask>().eq("task_status","terminate")
                .eq("enable",1)
                .eq("module",module);
        if (extraSQL!=null){
            if (extraSQL.getSqlTemplate()!=null){
                ew = ew.addFilter(extraSQL.getSqlTemplate());
            }
            if (extraSQL.getOrderByColumn()!=null){
                ew = ew.orderBy(extraSQL.getOrderByColumn(),extraSQL.getIsAsc());
            }
        }
        PageHelper.startPage(page,pageSize);
        List<CarsokTenureTask> list = carsokTenureTaskMapper.queryTaskBySqlStatement(ew);
        return new PageInfo<>(list);
    }



    /**
    * @author zhangdi
    * @date 2017/11/20 11:13
    * @Description: 潜客购车结束所有任务
    */
    @Override
    public void finishTask(Integer cusId) {
        CarsokCustomer cus = new CarsokCustomer().selectById(cusId);
        if (cus != null) {
            if (("D 已成交").equals(cus.getLevel())) {
                   List<CarsokTenureTask> carsokTenureTask = new CarsokTenureTask().selectList(new EntityWrapper().eq("business_id",cusId));
                for (CarsokTenureTask tenureTask : carsokTenureTask) {
                    CarsokTenureTask c = new CarsokTenureTask();
                    c.setId(tenureTask.getId());
                    c.setTaskStatus(TaskStatusEnums.finish.name());
                    c.updateById();
                }
            }
        }

    }
}
