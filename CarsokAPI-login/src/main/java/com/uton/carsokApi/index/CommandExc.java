package com.uton.carsokApi.index;

import com.uton.carsokApi.service.core.MessageSplitImpl;
import com.uton.carsokApi.service.core.SpringContextTool;
import com.utonw.searchcenter.api.command.ICommandExec;
import com.utonw.searchcenter.api.model.IndexBaseObject;

/**
 * Created by WANGYJ on 2017/9/16.
 */

public class CommandExc implements ICommandExec {
    public void sndMessageToQueue(IndexBaseObject data){
        try {
            MessageSplitImpl messageSplit = (MessageSplitImpl)SpringContextTool.getApplicationContext().getBean("bulksend");
            messageSplit.sndMessageBulk(data);
        }catch (Exception e){}

    }
}
