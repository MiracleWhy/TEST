package com.uton.carsokApi.service.core;
import com.alipay.api.internal.parser.json.ObjectJsonParser;
import com.utonw.searchcenter.api.model.IndexBaseObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by WANGYJ on 2017/9/18.
 */
@Component
public class ThreadPoolQueue {
    private static final Logger logger = Logger.getLogger(ThreadPoolQueue.class);
    @Value("${config.searchindex.threadcount}")
    private int threadCount;

    @Value("${config.searchindex.topic}")
    private String topic;

    @Value("${config.searchindex.key}")
    private String key;

    @Autowired
    private KafkaSendService kafkaSendService;

    private ExecutorService e = null;

    @PostConstruct
    public void StartTHread(){
        e = Executors.newFixedThreadPool(threadCount);
    }

    public void SndMessageByThreadPool(IndexBaseObject indexBaseObject){
        CountDownLatch threadlatch = new CountDownLatch(1);
        SendResult rs = new SendResult();
        e.execute(new PollingSnd(indexBaseObject,threadlatch,rs));
    }
    class PollingSnd implements Runnable{

        private CountDownLatch latch = null;
        private SendResult sendResult;
        IndexBaseObject indexBaseObject;
        //失败重试次数
        int retryCount = 3;

        public PollingSnd(IndexBaseObject indexBaseObject, CountDownLatch latch, SendResult sendResult){
            this.indexBaseObject = indexBaseObject;
            this.latch = latch;
            this.sendResult = sendResult;
        }
        public void setFailureResut(String errMsg){
            sendResult.setErrorMsg(errMsg);
            sendResult.setOk(false);
            latch.countDown();
        }
        public void setSuccessResut(String errMsg){
            sendResult.setErrorMsg(errMsg);
            sendResult.setOk(true);
            latch.countDown();
        }
        @Override
        public void run(){
            while (true){
                //进行消息发送
                sendResult.setOk(false);
                kafkaSendService.sndMessage(topic,key,indexBaseObject, this);
                try{
                    //如果发送的返回非常快的情况下，直接返回OK，不需要进行加锁
                    if (!sendResult.isOk()){
                        latch.await();
                    }
                    if (sendResult.isOk()){
                        break;
                    }else {
                        retryCount = retryCount - 1;
                        if (retryCount == 0)
                            break;
                        latch = new CountDownLatch(1);
                        continue;
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
