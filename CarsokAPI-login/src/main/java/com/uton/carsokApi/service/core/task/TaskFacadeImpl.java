package com.uton.carsokApi.service.core.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.constants.enums.ModuleEnums;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.service.ICarsokTenureTaskService;
import com.uton.carsokApi.service.ITaskExecutor;
import com.uton.carsokApi.service.ITaskFacade;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by WANGYJ on 2017/11/10.
 */
@Service("itaskfacade")
public class TaskFacadeImpl implements ITaskFacade {
    Logger logger = Logger.getLogger(TaskFacadeImpl.class);
    @Autowired
    private ITaskExecutor taskExecutor;

    @Autowired
    private ICarsokTenureTaskService carsokTenureTaskService;

    /**
     * @Description: 创建任务
     * @Param:
     * @Return:
     * @author WANGYJ
     * @date 2017/11/11 13:43
     */
    @Override
    public int createTask(TaskInitParam param) {
        int carsokTenureTaskId = -1;
        if ((param.getBusiness_id() == null)||
                (param.getModule() == null)||
                (param.getType()==null)){
            return carsokTenureTaskId;
        }else {
            carsokTenureTaskId = carsokTenureTaskService.createTenureTask(param);
        }
        return carsokTenureTaskId;
    }

    /**
     * @Description: 根据状态查询任务
     * @Param:
     * @Return: 分页结果数据
     * @author WANGYJ
     * @date 2017/11/13 0:15
     */
    @Override
    public PageInfo<CarsokTenureTask> queryTaskByStatusWithSQL(Integer page, Integer pageSize, TaskStatusEnums status, ModuleEnums module, FilterSQLParam sqlStatement) {
        return carsokTenureTaskService.queryTasksByStatusAndExtraSQL(page,pageSize,status,module.name(),sqlStatement);
    }

    /**
     * @Description: 查询分页
     * @Param: rowBounds 分页条件，sqlStatement查询条件
     * @Return: List<CarsokTenureTask>
     * @author WANGYJ
     * @date 2017/11/11 13:53
     */
    @Override
    public PageInfo<CarsokTenureTask> queryTaskBySQLFilter(Integer page, Integer pageSize, FilterSQLParam sqlStatement) {
        return carsokTenureTaskService.queryTasks(page,pageSize,sqlStatement);
    }

    /**
     * @Description: 通过entitywapper查询任务
     * @Param:  entityWrapper
     * @Return:
     * @author WANGYJ
     * @date 2017/11/12 15:38
     */
    @Override
    public PageInfo<CarsokTenureTask> queryTaskByEntityWrapper(Integer page, Integer pageSize, EntityWrapper<CarsokTenureTask> entityWrapper) {
        return carsokTenureTaskService.queryTasksByEntityWrapper(page, pageSize, entityWrapper);
    }

    /**
     * @Description: 根据任务id更新任务状态
     * @Param: taskId:任务id,assignAccount任务执行的主账号，childAccount任务执行的子账号
     * @Return:  任务执行的结果
     * @author WANGYJ
     * @date 2017/11/12 15:11
     */
    @Override
    public Boolean finishTaskById(int taskId, String assignAccount, String childAccount) {
        Boolean result = false;
        //验证账号或者子账号
        if ((assignAccount == null)&&(childAccount == null)){
            //任务执行者不能为空
            logger.error("任务执行账号不能同时为空。");
            return result;
        }
        if ((assignAccount != null)&&(childAccount != null)){
            logger.error("任务执行人不能有两个");
            return result;
        }
        //查询对象信息
        CarsokTenureTask carsokTenureTask = carsokTenureTaskService.selectOne(new EntityWrapper<CarsokTenureTask>().eq("id",taskId).eq("enable",1));
        if (carsokTenureTask == null){
            logger.error("任务id:"+taskId+" 不存在!");
            return result;
        }
        //对象信息检查
        if (carsokTenureTask.getTaskStatus().equals("finish")){
            logger.error("任务已经是完成状态了，不能再次完成");
        }
        //设置对象状态信息
        carsokTenureTask.setTaskStatus(TaskStatusEnums.finish.name());
        carsokTenureTask.setActualFinishTime(new Date());
        carsokTenureTask.setUpdateTime(new Date());
        if (assignAccount != null){
            carsokTenureTask.setAssigneeAccount(assignAccount);
        }
        if (childAccount != null){
            carsokTenureTask.setAssigneeChild(childAccount);
        }
        result = carsokTenureTaskService.updateById(carsokTenureTask);
        if (!result){
            logger.error("任务状态更新失败。");
        }
        return result;
    }

    @Override
    public Boolean updateExtraData(int taskId, Map<String, String> extraMap, String assignAccount, String assignChildId) {
        Boolean result = false;
        //查询对象信息
        CarsokTenureTask carsokTenureTask = carsokTenureTaskService.selectOne(new EntityWrapper<CarsokTenureTask>().eq("id",taskId).eq("enable",1));
        if (carsokTenureTask == null){
            logger.error("任务id:"+taskId+" 不存在!");
            return result;
        }
        //检查任务更新人, 定时任务处理时, 可能没有任务更新人，因此删除。
//        if ((assignAccount == null)&&(assignChildId == null)){
//            logger.error("任务更新人，不能为空。");
//            return result;
//        }
        //检查任务更新人
        if ((assignAccount != null)&&(assignChildId != null)){
            logger.error("任务更新人，不能同时有两个人。");
            return result;
        }
        JSONObject jsonObject = JSONObject.fromObject(extraMap);
        carsokTenureTask.setExtraFields(jsonObject.toString());
        carsokTenureTask.setUpdatebyAccountid(assignAccount);
        carsokTenureTask.setUpdatebyChildid(assignChildId);
        carsokTenureTask.setUpdateTime(new Date());
        result = carsokTenureTaskService.updateById(carsokTenureTask);
        if (!result){
            logger.error("更新任务失败:"+taskId);
        }
        return result;
    }

    /**
     * @Description: 删除任务
     * @Param:
     * @Return:
     * @author WANGYJ
     * @date 2017/11/12 16:00
     */
    @Override
    public Boolean deleteTaskById(int taskId, String assignAccount, String childAccount) {
        Boolean result = false;
        //检查任务更新人,因为定时任务终止时，可能没有任务更新人，因此删除。
//        if ((assignAccount == null)&&(childAccount==null)){
//            logger.error("任务更新人，不能为空。");
//            return result;
//        }
        if ((assignAccount != null)&&(childAccount!=null)){
            logger.error("任务更新人，不能同时有两个人。");
            return result;
        }
        //查询对象信息
        CarsokTenureTask carsokTenureTask = carsokTenureTaskService.selectOne(new EntityWrapper<CarsokTenureTask>().eq("id",taskId).eq("enable",1));
        if (carsokTenureTask == null){
            logger.error("任务id:"+taskId+" 不存在!");
            return result;
        }
        //检查任务状态(任务是finishi状态，不能进行终止）
        if (carsokTenureTask.getTaskStatus().equals(TaskStatusEnums.finish.name())){
           logger.error("任务是已完成状态，不进行终止");
           return false;
        }
        //检查任务状态(任务是termiate状态，不能进行终止）
        if (carsokTenureTask.getTaskStatus().equals(TaskStatusEnums.terminate.name())){
            logger.error("任务是终止状态，不进行再次终止");
            return false;
        }
        //更新任务状态为终止状态，同时添加任务执行人
        carsokTenureTask.setUpdateTime(new Date());
        carsokTenureTask.setTaskStatus(TaskStatusEnums.terminate.name());
        carsokTenureTask.setUpdatebyChildid(childAccount);
        carsokTenureTask.setUpdatebyAccountid(assignAccount);
        result = carsokTenureTaskService.updateById(carsokTenureTask);
        if (!result){
            logger.error("删除任务失败:"+taskId);
        }
        return result;
    }
}
