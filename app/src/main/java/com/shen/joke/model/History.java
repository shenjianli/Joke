package com.shen.joke.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by shenjianli on 17/8/15.
 * 记录客户端本地更新信息
 */
@Entity
public class History {

    //更新记录id
    @Id(autoincrement = true)
    private Long id;

    //表示最新的joke 时间
    @Property(nameInDb = "update_date")
    private String upDate;

    //本记录写入时间
    @Property(nameInDb = "his_date")
    private String hisDate;

    //表示最新的joke Id
    @Property(nameInDb = "his_id")
    private String updateId;

    @Generated(hash = 558918969)
    public History(Long id, String upDate, String hisDate, String updateId) {
        this.id = id;
        this.upDate = upDate;
        this.hisDate = hisDate;
        this.updateId = updateId;
    }

    @Generated(hash = 869423138)
    public History() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpDate() {
        return this.upDate;
    }

    public void setUpDate(String upDate) {
        this.upDate = upDate;
    }

    public String getHisDate() {
        return this.hisDate;
    }

    public void setHisDate(String hisDate) {
        this.hisDate = hisDate;
    }

    public String getUpdateId() {
        return this.updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", upDate='" + upDate + '\'' +
                ", hisDate='" + hisDate + '\'' +
                ", updateId='" + updateId + '\'' +
                '}';
    }
}
