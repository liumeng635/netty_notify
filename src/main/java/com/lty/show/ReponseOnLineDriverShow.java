package com.lty.show;

/**
 *  返回司机状态
 * @author zhouyongbo
 */
public class ReponseOnLineDriverShow {

    private Integer driverId;

    private String cityCode;

    private String driverName;

    private String account;

    public ReponseOnLineDriverShow(Integer driverId, String cityCode,
                                   String driverName, String account) {
        this.driverId = driverId;
        this.cityCode = cityCode;
        this.driverName = driverName;
        this.account = account;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
