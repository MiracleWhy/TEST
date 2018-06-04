package com.uton.carsokApi.service.core;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.uton.carsokApi.model.CarsokCustomerIntentionCar;
import com.uton.carsokApi.model.CarsokDiffmap;
import com.uton.carsokApi.pay.alipay.util.StringUtil;
import com.uton.carsokApi.service.ICarsokDiffmapService;
import com.uton.carsokApi.service.IObjectDiffExcutor;
import com.uton.carsokApi.util.DozerMapperUtil;
import com.uton.carsokApi.util.StringFormatUitl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WANGYJ on 2017/11/8.
 */
public class ObjectDiffExcutor<T> implements IObjectDiffExcutor<T> {
    Logger logger = Logger.getLogger(ObjectDiffExcutor.class);

    public String compareObjectWitTemplate(T oldObj, T newObj, int module){
        //定义返回的字符串
        String result = "";
        //定义文案字符串
        String template = "%s %s修改为%s";
        //检查对象类名称是否一致
        if (!oldObj.getClass().getName().equals(newObj.getClass().getName())){
            logger.error("oldClass:"+oldObj.getClass().getName()+",newObj:"+newObj.getClass().getName()+"不相同，请检查");
        }
        ICarsokDiffmapService carsokDiffmapService = (ICarsokDiffmapService)SpringContextTool.getApplicationContext().getBean("diffmapservice");
        //查询所有需要查分的列
        List<CarsokDiffmap> carsokDiffmaps = carsokDiffmapService.selectList(new EntityWrapper<CarsokDiffmap>().eq("module",module).eq("className",oldObj.getClass().getName()));
        //将对象转换成map
        Map<String,Object> oldMap = new HashMap<>();
        DozerMapperUtil.getInstance().map(oldObj,oldMap);
        Map<String,Object> newMap = new HashMap<>();
        DozerMapperUtil.getInstance().map(newObj,newMap);
        //将map进行对比
        for (CarsokDiffmap carsokDiffmap:carsokDiffmaps){
            String oldStr = null;
            if (oldMap.get(carsokDiffmap.getProp())!=null){
                oldStr = oldMap.get(carsokDiffmap.getProp()).toString();
            }
            String newStr = null;
            if ( newMap.get(carsokDiffmap.getProp())!=null){
                newStr = newMap.get(carsokDiffmap.getProp()).toString();
            }
            if (StringUtil.isEmpty(oldStr)){
                oldStr = "空";
            }else {
                oldStr = StringFormatUitl.formatStringByType(oldStr,carsokDiffmap.getType(),carsokDiffmap.getFormat(),carsokDiffmap.getSuffix());
            }
            if (StringUtil.isEmpty(newStr)){
                newStr = "空";
            }else{
                newStr = StringFormatUitl.formatStringByType(newStr,carsokDiffmap.getType(),carsokDiffmap.getFormat(),carsokDiffmap.getSuffix());
            }
            if (!oldStr.equals(newStr)){
                //如果是空字符串的话，前面不加逗号
                if (result.equals("")){
                    result += String.format(template, carsokDiffmap.getPropname(),oldStr,newStr);
                }else {
                    result = result.concat(",");
                    result += String.format(template, carsokDiffmap.getPropname(),oldStr,newStr);
                }
            }

        }

        return result;
    }

    /**
     * @Description: 对比两个对象是否不同
     * @Param: oldObj,newObj
     * @Return: isDifferent
     * @author WANGYJ
     * @date 2017/11/8 20:04
     */
    private boolean compareObjectIsDifferent(Object oldObj, Object newObj, int module){
        boolean isDifferent = false;
        //检查对象类名称是否一致
        if (!oldObj.getClass().getName().equals(newObj.getClass().getName())){
            logger.error("oldClass:"+oldObj.getClass().getName()+",newObj:"+newObj.getClass().getName()+"不相同，请检查");
        }
        ICarsokDiffmapService carsokDiffmapService = (ICarsokDiffmapService)SpringContextTool.getApplicationContext().getBean("diffmapservice");
        //查询所有需要查分的列
        List<CarsokDiffmap> carsokDiffmaps = carsokDiffmapService.selectList(new EntityWrapper<CarsokDiffmap>().eq("module",module).eq("className",oldObj.getClass().getName()));
        //将对象转换成map
        Map<String,Object> oldMap = new HashMap<>();
        DozerMapperUtil.getInstance().map(oldObj,oldMap);
        Map<String,Object> newMap = new HashMap<>();
        DozerMapperUtil.getInstance().map(newObj,newMap);
        //将map进行对比
        for (CarsokDiffmap carsokDiffmap:carsokDiffmaps){
            String oldStr = null;
            if (oldMap.get(carsokDiffmap.getProp())!=null){
                oldStr = oldMap.get(carsokDiffmap.getProp()).toString();
            }
            String newStr = null;
            if ( newMap.get(carsokDiffmap.getProp())!=null){
                newStr = newMap.get(carsokDiffmap.getProp()).toString();
            }
            //同时为空的情况下，结果为相同
            if((oldStr == null)&&(newStr == null)){
                return isDifferent;
            }
            //有一个为空的情况下，结果为不同
            if ((!(oldStr == null))&&(newStr == null)){
                isDifferent = true;
                return isDifferent;
            }
            if ((oldStr == null)&&(!(newStr == null))){
                isDifferent = true;
                return isDifferent;
            }
            //都不为空的情况下
            if (!oldStr.equals(newStr)){
                isDifferent = true;
                break;
            }

        }

        return isDifferent;
    }
    /**
     * @Description: 针对List内容进行差分共同处理方法
     * @Param: oldList,newList
     * @Return: 差分String
     * @author WANGYJ
     * @date 2017/11/8 19:28
     */
    public String compareListObject(List<T> oldList, List<T> newList, int module) {
        //文言模板
        String template = "%s 修改为 %s";
        if ((oldList!=null)&&(newList!=null)){
            if (!oldList.getClass().getName().equals(newList.getClass().getName())){
                //如果对象的类型不相同，无法进行比较，直接返回null同时打印log
                logger.error("对象类型不同，无法比较，请确认");
                return null;
            }
        }
        if ((oldList!=null)&&(newList!=null)){
            if (oldList.size()== newList.size()){
                boolean isDiffrent = false;
                for (Object oldObject:oldList){
                    for (Object newObject: newList){
                        isDiffrent = compareObjectIsDifferent(oldObject,newObject,module);
                    }
                }
                //如果相同就返回null
                if (!isDiffrent)
                    return null;
            }
        }

        //具体对比结果
        //变更前的字符串
        String oldStr = "空";
        String newStr = "空";
        if ((oldList !=null)&&(oldList.size()!=0)){
            oldStr = convertListObject(oldList,module);
        }
        if ((newList !=null)&&(newList.size()!=0)){
            newStr = convertListObject(newList,module);
        }
        String result = String.format(template,oldStr,newStr);
        return result;
    }

    /**
     * @Description: 将列表数据转换为String，不同对象之间用逗号进行分割
     * @Param: objectList 对象列表
     * @Return:
     * @author WANGYJ
     * @date 2017/11/8 21:08
     */
    private String convertListObject(List<T> objectList, int module){
        String result = null;
        T obj = objectList.get(0);
        ICarsokDiffmapService carsokDiffmapService = (ICarsokDiffmapService)SpringContextTool.getApplicationContext().getBean("diffmapservice");
        //查询所有需要查分的列
        List<CarsokDiffmap> carsokDiffmaps = carsokDiffmapService.selectList(new EntityWrapper<CarsokDiffmap>().eq("module",module).eq("className",obj.getClass().getName()));
        int i = 0;
        for (T sourceObj:objectList){
            if (i!=0){
                result += ",";
            }
            i++;
            //将对象转换成map
            Map<String,T> sourceMap = new HashMap<>();
            DozerMapperUtil.getInstance().map(sourceObj,sourceMap);
            for (CarsokDiffmap carsokDiffmap:carsokDiffmaps){
                T prop = sourceMap.get(carsokDiffmap.getProp());
                if (prop != null){
                    if (result!=null){
                        result = result+" "+prop.toString();
                    }else {
                        result = prop.toString();
                    }
                }
            }
        }
        return result;
    }

}
