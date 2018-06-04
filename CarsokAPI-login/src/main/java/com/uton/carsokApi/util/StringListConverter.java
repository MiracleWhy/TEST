package com.uton.carsokApi.util;

import net.sf.json.JSONArray;
import org.dozer.DozerConverter;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
public class StringListConverter extends DozerConverter<String,List> {
    public StringListConverter() {
        super(String.class, List.class);
    }
    @Override
    public List<String> convertTo(String s, List list) {
        JSONArray jsonArray = JSONArray.fromObject(s);
        List<String> lists = JSONArray.toList(jsonArray,String.class);
        return lists;
    }

    @Override
    public String convertFrom(List list, String s) {
        return list.toString();
    }
}
