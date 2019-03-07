package com.lty.socket.packet.appDriver.base;


/**
 *  body追加内容
 * @author zhouyongbo
 */
public class AppendBody {

    private String devSn;
    private Float appVn;
    private String token;
    private Float protocolVersion;
    private Integer devType;

    public String getDevSn() {
        return devSn;
    }

    public Float getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(Float protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }

    public Float getAppVn() {
        return appVn;
    }

    public void setAppVn(Float appVn) {
        this.appVn = appVn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getDevType() {
        return devType;
    }

    public void setDevType(Integer devType) {
        this.devType = devType;
    }
}
