package com.uton.carsokApi.service.core;

import com.utonw.searchcenter.api.enums.SearchIndexMessageType;
import com.utonw.searchcenter.api.model.IndexBaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANGYJ on 2017/9/18.
 * 针对应用发送的消息，根据固定的大小，划分成多个小package
 * 发送通过kafka发送给消息队列。
 */
@Service("bulksend")
public class MessageSplitImpl {

    @Value("${config.searchindex.maxpackage}")
    private Integer maxPackage;

    @Autowired
    private ThreadPoolQueue threadPoolQueue;

    public void sndMessageBulk(IndexBaseObject indexBaseObject){
        if (indexBaseObject.getOperation().equals(SearchIndexMessageType.batchDelete.toString())||
                (indexBaseObject.getOperation().equals(SearchIndexMessageType.batchInsert.toString()))||
                (indexBaseObject.getOperation().equals(SearchIndexMessageType.batchUpdate.toString()))){
            List list = (List)indexBaseObject.getData();
            int size = list.size();

            //进行拆包发送
            for (int i = 0; i < size; ){
                List target = new ArrayList();
                for (int j =0; (j< maxPackage)&&(i<size); j++){
                    //向目标列表中进行copy
                    target.add(list.get(i++));
                }
                //把copy完成后的对象发送给
                IndexBaseObject tempObject = new IndexBaseObject();
                tempObject.setOperation(indexBaseObject.getOperation());
                tempObject.setData(target);
                tempObject.setIndexName(indexBaseObject.getIndexName());
                tempObject.setAppName(indexBaseObject.getAppName());
                threadPoolQueue.SndMessageByThreadPool(tempObject);
            }
        }else{
            //直接进行发送
            threadPoolQueue.SndMessageByThreadPool(indexBaseObject);
        }
    }
}
