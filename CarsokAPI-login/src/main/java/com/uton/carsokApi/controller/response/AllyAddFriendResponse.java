package com.uton.carsokApi.controller.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raytine on 2017/10/18.
 */
public class AllyAddFriendResponse implements Serializable{

    /**
     * 新的盟友列表
     */
    private List<AllyResponse> newFriendList;

    /**
     * 推荐盟友列表
     */
    private List<AllyResponse> localFriendList;

    public List<AllyResponse> getNewFriendList() {
        return newFriendList;
    }

    public void setNewFriendList(List<AllyResponse> newFriendList) {
        this.newFriendList = newFriendList;
    }

    public List<AllyResponse> getLocalFriendList() {
        return localFriendList;
    }

    public void setLocalFriendList(List<AllyResponse> localFriendList) {
        this.localFriendList = localFriendList;
    }
}
