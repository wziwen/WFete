package com.wziwen.spider;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by ziwen.wen on 2017/2/10.
 * 任务队列
 */
public class Task {

    @DatabaseField(generatedId = true)
    private int id;
    /**
     * 名称
     */
    @DatabaseField
    private String name;
    /**
     * fetcherType
     */
    @DatabaseField
    private int fetcherType;
    /**
     * parserType
     */
    @DatabaseField
    private int parserType;
    /**
     * url, 连接, 不能重复, 不能为空
     */
    @DatabaseField(unique = true, canBeNull = false)
    private String url;
    /**
     */
    @DatabaseField
    private String extra;
    /**
     * 创建时间
     */
    @DatabaseField
    private long createTime;
    /**
     * 更新时间
     */
    @DatabaseField
    private long updateTime;
    /**
     */
    @DatabaseField
    private int priority;

    /**
     */
    @DatabaseField
    private int finished;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFetcherType() {
        return fetcherType;
    }

    public void setFetcherType(int fetcherType) {
        this.fetcherType = fetcherType;
    }

    public int getParserType() {
        return parserType;
    }

    public void setParserType(int parserType) {
        this.parserType = parserType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fetcherType=" + fetcherType +
                ", parserType=" + parserType +
                ", url='" + url + '\'' +
                ", extra='" + extra + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", priority=" + priority +
                ", finished=" + finished +
                '}';
    }
}
