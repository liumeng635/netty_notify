package com.lty.socket.packet.domain;

import com.lty.show.BaseDriverShow;
import com.lty.socket.netty.filter.StringDecode;
import com.lty.socket.packet.appDriver.base.AppendBody;
import com.lty.util.StringUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * session 记录
 * @author zhouyongbo
 */
public class SessionRecord {
    /**
     * 链接时间
     */
    private Long recordDate;

    /**
     * 链接
     */
    private Channel channel;

    /**
     * 解码器
     */
    private StringDecode stringDecode = new StringDecode();

    /**
     * 协议附加信息信息
     */
    private AppendBody appendBody;

    /**
     * 是否完成鉴权 1:是 0：否
     */
    private int isLogin = 0;


    private String token;

    private Integer driverId;

    private String driverAccount;

    private String driverPhone;

    private Map<Integer,NoticePacket> callBackPath = new HashMap<Integer, NoticePacket>();

    private BaseDriverShow baseDriverShow;


    public SessionRecord(Long recordDate, Channel channel) {
        this.recordDate = recordDate;
        this.channel = channel;
    }


    public SessionRecord(String token, Integer driverId, String driverAccount, String driverPhone) {
        this.token = token;
        this.driverId = driverId;
        this.driverAccount = driverAccount;
        this.driverPhone = driverPhone;
    }

    public SessionRecord(Long recordDate, String token, Integer driverId,
                         String driverAccount, String driverPhone, BaseDriverShow baseDriverShow) {
        this.recordDate = recordDate;
        this.token = token;
        this.driverId = driverId;
        this.driverAccount = driverAccount;
        this.driverPhone = driverPhone;
        this.baseDriverShow = baseDriverShow;
    }

    public Map<Integer, NoticePacket> getCallBackPath() {
        return callBackPath;
    }

    public void setCallBackPath(Map<Integer, NoticePacket> callBackPath) {
        this.callBackPath = callBackPath;
    }

    @Override
    public String toString() {
        return "SessionRecord{" +
                "recordDate=" + recordDate +
                ", channel=" + (channel !=null ?channel.isOpen():null) +
                ", isLogin=" + isLogin +
                ", token='" + token + '\'' +
                ", driverId=" + driverId +
                ", driverPhone='" + driverPhone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SessionRecord){
            SessionRecord sessionRecord = (SessionRecord) obj;
            if ((StringUtil.isBank(token)&& token.equals(sessionRecord.getToken())) ||
                    (driverId != null && driverId.equals(sessionRecord.getDriverId()))
                    || (StringUtil.isBank(driverAccount) && driverAccount.equals(sessionRecord.getDriverAccount()))
                     || (StringUtil.isBank(driverPhone) && driverPhone.equals(sessionRecord.getDriverPhone()))
                    || ( channel != null &&channel.equals(sessionRecord.getChannel()))){
                return true;
            }
            return false;
        }
        return super.equals(obj);
    }


    public AppendBody getAppendBody() {
        return appendBody;
    }

    public void setAppendBody(AppendBody appendBody) {
        this.appendBody = appendBody;
    }

    public StringDecode getStringDecode() {
        return stringDecode;
    }

    public void setStringDecode(StringDecode stringDecode) {
        this.stringDecode = stringDecode;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Long recordDate) {
        this.recordDate = recordDate;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
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

    public BaseDriverShow getBaseDriverShow() {
        return baseDriverShow;
    }

    public void setBaseDriverShow(BaseDriverShow baseDriverShow) {
        this.baseDriverShow = baseDriverShow;
    }
}
