package com.lty.entity.primary;

/**
 * 班次信息
 * @author zhouyongbo
 */
public class ScheduleTripTime {

    private Integer id;
    /**
     * 班次ID
     */
    private String scheduleId;

    /**
     * 行程ID
     */
    private Integer scheduleTripId;
    /**
     * 站点ID
     */
    private Integer stationId;
    /**
     * 线路id
     */
    private Integer routeId;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 到站时间
     */
    private String stationTime;


    /**
     * 车辆id
     */
    private Integer busId;

    /**
     * 创建时间
     */
    private String createTime;


    public Integer getScheduleTripId() {
        return scheduleTripId;
    }

    public void setScheduleTripId(Integer scheduleTripId) {
        this.scheduleTripId = scheduleTripId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationTime() {
        return stationTime;
    }

    public void setStationTime(String stationTime) {
        this.stationTime = stationTime;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}