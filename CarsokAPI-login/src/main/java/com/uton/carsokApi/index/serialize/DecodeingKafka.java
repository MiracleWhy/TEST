package com.uton.carsokApi.index.serialize;


import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * Created by WANGYJ on 2017/9/19.
 */
public class DecodeingKafka implements Deserializer<Object> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        return BeanUtils.byte2Obj(data);
    }

    @Override
    public void close() {

    }

}