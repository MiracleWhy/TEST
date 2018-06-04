package com.uton.carsApi.test.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;


public class WriteProperties {

  public static void writeProperties(String testMapper,String propertyFile) throws IOException, URISyntaxException {

    InputStream input = WriteProperties.class.getResourceAsStream(propertyFile);
    Properties prop = new Properties();
    prop.load(input);
    input.close();

    prop.setProperty("junit.mapper", testMapper);
    URL url = WriteProperties.class.getResource(propertyFile);
    File file = new File(url.toURI());
    OutputStream output = new FileOutputStream(file);

    prop.store(output, "DictMapper");
    output.flush();
    output.close();
  }
}
