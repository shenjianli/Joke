package com.shen.joke.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by shenjianli on 17/8/15.
 */
@Entity
public class History {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "update_date")
    private String upDate;

    @Property(nameInDb = "his_date")
    private String hisDate;

    @Generated(hash = 1399251433)
    public History(Long id, String upDate, String hisDate) {
        this.id = id;
        this.upDate = upDate;
        this.hisDate = hisDate;
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
}
