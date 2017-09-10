package com.shen.joke.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by shenjianli on 17/8/15.
 */
@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "s_id")
    private String employeeId;
    @Property(nameInDb = "s_name")
    private String name;
    @Property(nameInDb = "s_pass")
    private String pass;
    @Property(nameInDb = "s_device")
    private String deviceId;

    @Generated(hash = 170444111)
    public User(Long id, String employeeId, String name, String pass,
                String deviceId) {
        this.id = id;
        this.employeeId = employeeId;
        this.name = name;
        this.pass = pass;
        this.deviceId = deviceId;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPass() {
        return this.pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object user) {
        if(user instanceof User) {
            if(name.equals(((User) user).getName())
                    && pass.equals(((User) user).getPass())
                    && deviceId.equals(((User) user).getDeviceId())){
                return true;
            }
        }
        return false;
    }
    public String getEmployeeId() {
        return this.employeeId;
    }
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
