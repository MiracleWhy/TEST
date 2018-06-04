package com.uton.carsokApi.controller.response;

import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

/**
 * 登录res
 * @author bing.cheng
 *
 */
public class HuanXinResponse {
	private String nick;
    private String headPath;

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
