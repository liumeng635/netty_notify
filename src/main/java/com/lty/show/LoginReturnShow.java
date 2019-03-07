package com.lty.show;


/**
 * 登录返回信息
 * @author zhouyongbo
 */
public class LoginReturnShow {

    private int driverId;

    private String name;

    private String account;
    private String token;

    public LoginReturnShow(int driverId, String name, String token) {
        this.driverId = driverId;
        this.name = name;
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
