package com.uton.carsApi.test.dao;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.uton.carsApi.test.util.WriteProperties;
import com.uton.carsokApi.dao.AppVersionMapper;
import com.uton.carsokApi.model.AppVersion;


public class AppVersionMapperTest {
  String testMapper = "classpath:com/uton/carsokApi/mapping/AppVersionMapper.xml";
  String propertyFile = "/properties/mybaties.properties";

  @Before
  public void writeProperties() throws IOException, URISyntaxException {
    WriteProperties.writeProperties(testMapper, propertyFile);
  }
  
  @Test
  public void TestDictMapperDao() {
    ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext(new String[] {
            "classpath:/xmlConfig/junit_context.xml"});

    AppVersionMapper appVersionMapper = context.getBean(AppVersionMapper.class);
    AppVersion a=appVersionMapper.selectByPrimaryKey(1);
    System.out.println(JSON.toJSONString(a));
  }
}
