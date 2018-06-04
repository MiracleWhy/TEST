package com.uton.carsokApi.index;

import com.utonw.searchcenter.api.command.IOperation;

/**
 * Created by WANGYJ on 2017/9/21.
 */
public class IndexSendHelper {
    public static void sendIndexCommandToMQ(IOperation operation){
        CommandExc exc = new CommandExc();
        operation.setCommandOperator(exc);
        IndexUpdateExecutor indexUpdateExecutor = new IndexUpdateExecutor();
        indexUpdateExecutor.setOperation(operation);
        indexUpdateExecutor.updateIndex();
    }
}
