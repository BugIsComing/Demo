package com.lc.model;

import com.google.gson.annotations.Expose;
import com.lc.tools.JsonHelper;


import java.io.Serializable;
import java.util.Map;

/**
 * @author ifly_lc
 * 咪咕视讯注册用户的请求参数
 */
public class QueryMiguVideoRegisterReq implements Serializable {
    /**
     * 账号名
     */
    @Expose
    private String accountName;
    /**
     * 账号类型
     */
    @Expose
    private String accountType;
    /**
     * 时间戳
     */
    @Expose
    private long timestamp;
    /**
     * 附加属性
     */
    @Expose
    private Map<String,String> extInfo;

    public QueryMiguVideoRegisterReq() {
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timeStamp) {
        this.timestamp = timeStamp;
    }

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }
    @Override
    public String toString() {
        return JsonHelper.getJsonString(this);
    }

}
