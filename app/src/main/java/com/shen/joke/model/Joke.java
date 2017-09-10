package com.shen.joke.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ljq on 2017/9/9.
 */
@Entity
public class Joke {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "joke_site")
    private String site;

    @Property(nameInDb = "joke_content")
    private String content;

    @Property(nameInDb = "joke_date")
    private String date;

    @Property(nameInDb = "joke_num")
    private int num;

    @Generated(hash = 545103526)
    public Joke(Long id, String site, String content, String date, int num) {
        this.id = id;
        this.site = site;
        this.content = content;
        this.date = date;
        this.num = num;
    }

    @Generated(hash = 2009425276)
    public Joke() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }


}
