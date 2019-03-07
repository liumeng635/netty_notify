package com.lty.config;

/**
 * 服务配置
 * @author zhouyongbo
 */
public class Server {
    private Integer port = 8080;
    private Integer tokenTimeout = 1800;

    public Integer getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(Integer tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
