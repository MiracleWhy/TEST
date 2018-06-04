package com.uton.carsokApi.index;


import com.utonw.searchcenter.api.command.IOperation;

/**
 * Created by WANGYJ on 2017/9/18.
 */
public class IndexUpdateExecutor {
    private IOperation operation;
    public void setOperation(IOperation operation){
        this.operation = operation;
    }
    public void updateIndex(){
        operation.execute();
    }
}