package com.uton.carsokApi.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class Pagebean<T> {
    private int tr;//总记录数
    private int pc;//当前页
    private int tp;//总页数
    private int pr;//每页记录数
    private List<T> beanlist;//数据
    private String content;
    private String url;
    public int getTr(Integer count) {
        return tr;
    }
    public void setTr(int tr) {
        this.tr = tr;
    }
    public int getPc() {
        return pc;
    }
    public void setPc(int pc) {
        this.pc = pc;
    }
    public int getTp() {
        tp = tr/pr;
        return tr%pr==0?tp:tp+1;
    }

    public int getPr() {
        return pr;
    }
    public void setPr(int pr) {
        this.pr = pr;
    }
    public List<T> getBeanlist() {
        return beanlist;
    }
    public void setBeanlist(List<T> beanlist) {
        this.beanlist = beanlist;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
