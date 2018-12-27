package com.qm.jsondemo.demo.model;

import com.qm.jsondemo.demo.util.JsonUtil;

/**
 * @author qiumin
 * @create 2018/12/24 18:20
 * @desc
 **/
public class Student {

    private Integer sid;

    private String stuName;


    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", stuName='" + stuName + '\'' +
                '}';
    }
}
