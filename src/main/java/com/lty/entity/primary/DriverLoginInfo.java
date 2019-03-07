package com.lty.entity.primary;

public class DriverLoginInfo {
    /**
     * 司机ID
     */
    private Integer driverId;

    /**
     * 设备编号
     */
    private String facilityNo;

    /**
     * 登录还是 退出  0：登录  1：退出
     */
    private Integer isLogin;

    /**
     * 设备类型
     */
    private Integer facilityType;

    private String createTime;

    /**
     * 协议版本
     */
    private String protocolVersion;

    /**
     * 终端软件版本
     */
    private String appVersion;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getFacilityNo() {
        return facilityNo;
    }

    public void setFacilityNo(String facilityNo) {
        this.facilityNo = facilityNo;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public Integer getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(Integer facilityType) {
        this.facilityType = facilityType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
}