package com.lty.show.parameter;


import io.netty.channel.Channel;

/**
 *  session 参数模型
 * @author zhouyongbo
 */
public class SessionRecordsParam {
    private String token;
    private Integer driverId;
    private String driverAccount;
    private String driverPhone;
    private Channel channel;

    public SessionRecordsParam(Channel channel) {
        this.channel = channel;
    }

    public SessionRecordsParam(String token, Integer driverId) {
        this.token = token;
        this.driverId = driverId;
    }

    public SessionRecordsParam(String token, Integer driverId,
                               String driverAccount, String driverPhone) {
        this.token = token;
        this.driverId = driverId;
        this.driverAccount = driverAccount;
        this.driverPhone = driverPhone;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getDriverAccount() {
        return driverAccount;
    }

    public void setDriverAccount(String driverAccount) {
        this.driverAccount = driverAccount;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }
}
