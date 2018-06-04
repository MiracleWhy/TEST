package com.uton.carsokApi.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.uton.carsokApi.constants.enums.ModuleEnums;
import com.uton.carsokApi.constants.enums.TaskStatusEnums;
import com.uton.carsokApi.constants.enums.TaskTypeEnums;
import com.uton.carsokApi.dao.CarsokRoleMapper;
import com.uton.carsokApi.dto.TaskInitParam;
import com.uton.carsokApi.model.CarsokRole;
import com.uton.carsokApi.model.CarsokTenureTask;
import com.uton.carsokApi.service.ICarsokRoleService;
import com.uton.carsokApi.service.ITaskFacade;
import com.uton.carsokApi.service.core.task.FilterSQLParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author wangyj
 * @since 2017-11-09
 */
@Service
public class CarsokRoleServiceImpl extends ServiceImpl<CarsokRoleMapper, CarsokRole> implements ICarsokRoleService {
    Logger logger = Logger.getLogger(CarsokRoleServiceImpl.class);
    @Autowired
    private ITaskFacade taskFacade;
/* 无用代码，，，废弃

    @PostConstruct
    public void test(){
        FilterSQLParam filterSQLParam = new FilterSQLParam();
        filterSQLParam.setSqlTemplate("id>1");
        PageInfo<CarsokTenureTask> page = taskFacade.queryTaskBySQLFilter(1,2,filterSQLParam);
        logger.error(page.getList().toString());
        TaskInitParam param = new TaskInitParam();
        param.setBusiness_id("1");;
        Calendar calendar = Calendar.getInstance();
        param.setScheduled_time(calendar.getTime());
        param.setModule(ModuleEnums.potentialcustomer);
        param.setType(TaskTypeEnums.annual_survey_remind);
        Map<String,String> map = new HashMap<>();
        map.put("merchantId","112");
        param.setExtraFields(map);
        int id = taskFacade.createTask(param);
        logger.error("创建结果为："+id);
        String extraSQL = "json_extract(extra_fields,'$.merchantId')='111' ";
        filterSQLParam.setSqlTemplate(extraSQL);
        PageInfo<CarsokTenureTask> list = taskFacade.queryTaskByStatusWithSQL(1,2, TaskStatusEnums.ready,ModuleEnums.potentialcustomer,filterSQLParam);
        logger.error("任务结果："+list.toString());
        Boolean t = taskFacade.finishTaskById(id,"1111",null);
        if (t){
            logger.error("完成任务成功");
        }else {
            logger.error("完成任务失败");
        }
    }
*/

}
