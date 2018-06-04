package com.uton.carsokApi.util;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANGYJ on 2017/9/20.
 */
public class DozerMapperUtil {
    private static Mapper instance;
    private static List myMappingFiles = new ArrayList();
    private DozerMapperUtil() {

    }

    public static synchronized Mapper getInstance() {
        if(instance == null) {
            instance = new DozerBeanMapper();
            myMappingFiles.add("classpath:xmlConfig/convert.xml");
            ((DozerBeanMapper)instance).setMappingFiles(myMappingFiles);
        }

        return instance;
    }
}
