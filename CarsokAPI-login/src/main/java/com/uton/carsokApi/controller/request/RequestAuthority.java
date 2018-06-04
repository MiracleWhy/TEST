package com.uton.carsokApi.controller.request;

import com.uton.carsokApi.util.DateStyle;
import com.uton.carsokApi.util.DateUtil;

import java.io.Serializable;
import java.util.*;

/**
 * Created by SEELE on 2018/2/1.
 */
public class RequestAuthority implements Serializable {
    private String token;
    private Queue<Long> refreshTimes;
    private String lastTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        setRefreshTime(System.currentTimeMillis());
    }

    public Queue<Long> getRefreshTimes() {
        return refreshTimes;
    }

    public void setRefreshTimes(Queue<Long> refreshTimes) {
        this.refreshTimes = refreshTimes;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public boolean setRefreshTime(Long refreshTimestamp) {
        try {
            if (this.refreshTimes == null) {
                this.refreshTimes = new LinkedList<>();
            }
            if (this.refreshTimes.size() >= 32) {
                this.refreshTimes.poll();
            }

            String timeStr = DateUtil.DateToString(new Date(refreshTimestamp), DateStyle.YYYY_MM_DD);
            if (timeStr.equals(this.lastTime)) {
                return false;
            } else {
                this.lastTime = timeStr;
                return this.refreshTimes.offer(refreshTimestamp);
            }
        }
        catch (Exception e)
        {

        }
        return false;
    }
}
