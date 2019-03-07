package com.lty.entity.primary;


/**
 *
 * 司机用户表
 * @author zhouyongbo
 */
public class BaseDriverUser {
    private int id;
    /**
     * 司机Id
     */
    private int driverId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 公交公司Id
     */
    private int busCompanyId;
    /**
     * 最近登录时间
     */
    private String lastLoginTime;
    /**
     * 用户状态  0:离线  1：在线
     */
    private int status;
    /**
     * 城市Id
     */
    private String cityCode;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String modifyTime;
    /**
     * 创建用户Id
     */
    private int createUserId;
    /**
     * 修改用户Id
     */
    private int modifyUserId;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBusCompanyId() {
        return busCompanyId;
    }

    public void setBusCompanyId(int busCompanyId) {
        this.busCompanyId = busCompanyId;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(int modifyUserId) {
        this.modifyUserId = modifyUserId;
    }
}
