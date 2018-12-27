package com.qm.jsondemo.demo.model;

import java.util.List;

/**
 * @author qiumin
 * @create 2018/12/24 18:22
 * @desc
 **/
public class Classes {

    private Integer cid;

    private String classesName;

    private List<Student> studentList;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "cid=" + cid +
                ", classesName='" + classesName + '\'' +
                ", studentList=" + studentList +
                '}';
    }
}
