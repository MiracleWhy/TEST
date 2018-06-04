package com.uton.carsokApi.service.core;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;


/**
 * Created by WANGYJ on 2017/9/18.
 */
@Service
public class KafkaSendService {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public boolean sndMessage(final String topic, final String key,final Object o, final ThreadPoolQueue.PollingSnd sender){
        boolean result = false;
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic,key,o);
        future.addCallback(
                new ListenableFutureCallback<SendResult<Integer, String>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        logger.error("["+topic+"]"+
                                "["+key+"]"+
                                "["+o.toString()+"]"+throwable.getMessage());
                        //预留暂时不使用
                        sender.setFailureResut(throwable.getMessage());

                    }

                    @Override
                    public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
                        //预留暂时不使用
                        sender.setSuccessResut(integerStringSendResult.toString());
                    }
                }
        );
        kafkaTemplate.flush();
        return result;
    }
}
