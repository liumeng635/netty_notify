package com.lty.cache;

/**
 * @author zhouyongbo 2017/10/27
 *  缓存参数类型
 */
public class DataCache {
    /**
     * key值
     */
    private String key;
    /**
     * value 需要保存的数据
     */
    private String data;
    /**
     *  单位为秒 s   小于等于0为永久
     */
    private int timer;

    public DataCache() {
    }

    public DataCache(String key, String data) {
        this.key = key;
        this.data = data;
    }

    public DataCache(String key, String data, int timer) {
        this.key = key;
        this.data = data;
        this.timer = timer;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
