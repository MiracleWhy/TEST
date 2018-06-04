package com.uton.carsokApi.service.core;

import com.uton.carsokApi.controller.request.BZYCarDataRequest;
import com.uton.carsokApi.dao.ProductMapper;
import com.uton.carsokApi.index.IndexSendHelper;
import com.utonw.searchcenter.api.command.BatchInsert;
import com.utonw.searchcenter.api.command.BatchUpdate;
import com.utonw.searchcenter.api.command.SingleInsert;
import com.utonw.searchcenter.api.command.SingleUpdate;
import com.utonw.searchcenter.api.entity.StoreCar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SEELE on 2017/9/26.
 */
@Service
public class IndexSendService {
    @Value("${config.searchindex.indexname}")
    private String indexName;

    @Value("${config.searchindex.appname}")
    private String appName;

    @Value("${store.url.prefix}")
    private String staticAccessDomain;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductMapper productMapper;

    public void SingleInsertOrUpdate(Integer productId, boolean isInsert)
    {
        try {
            StoreCar storeCar = productMapper.SelectIndexById(Integer.valueOf(productId));
            storeCar.setCarType(2);//二手车(1:新车 2:二手车)
            storeCar.setChickPath(staticAccessDomain + storeCar.getProductCode() + ".html");
            this.SingleInsertOrUpdate(storeCar,isInsert);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
    }

    public void SingleInsertOrUpdate(StoreCar storeCar, boolean isInsert)
    {
        try {
            if (isInsert) {
                SingleInsert insert = new SingleInsert(appName, indexName, storeCar);
                IndexSendHelper.sendIndexCommandToMQ(insert);
            } else {
                SingleUpdate update = new SingleUpdate(appName, indexName, storeCar);
                IndexSendHelper.sendIndexCommandToMQ(update);
            }
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
        }
    }

    public void MultiInsertOrUpdate(String [] ids,boolean isInsert)
    {
        try {
            List<StoreCar> storeCars = productMapper.SelectIndexByIds(ids);
            for (StoreCar storeCar : storeCars) {
                storeCar.setCarType(2);//二手车(1:新车 2:二手车)
                storeCar.setChickPath(staticAccessDomain + storeCar.getProductCode() + ".html");
            }
            this.MultiInsertOrUpdate(storeCars, isInsert);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
    }

    public void MultiInsertOrUpdate(List<StoreCar> storeCars,boolean isInsert)
    {
        try {
            if (isInsert) {
                BatchInsert insert = new BatchInsert(appName, indexName, (List)storeCars);
                IndexSendHelper.sendIndexCommandToMQ(insert);
            } else {
                BatchUpdate update = new BatchUpdate(appName, indexName, (List)storeCars);
                IndexSendHelper.sendIndexCommandToMQ(update);
            }
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
        }
    }


}
