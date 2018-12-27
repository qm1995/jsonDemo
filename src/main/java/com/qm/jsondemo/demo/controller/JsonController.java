package com.qm.jsondemo.demo.controller;

import com.qm.jsondemo.demo.handler.RequestJson;
import com.qm.jsondemo.demo.model.Classes;
import com.qm.jsondemo.demo.model.Student;
import com.qm.jsondemo.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author qiumin
 * @create 2018/12/24 18:23
 * @desc
 **/
@RestController
public class JsonController {


    /**
     * 请求路径 http://127.0.0.1:8080/test 提交类型为application/json
     * 测试参数{"sid":1,"stuName":"里斯"}
     * @param str
     */
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void testJsonStr(@RequestBody String str){
        System.out.println(str);
    }

    /**
     * 请求路径 http://127.0.0.1:8080/test1 提交类型为application/json
     * 测试参数{"sid":1,"stuName":"里斯"}
     * @param students
     */
    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public void testJsonStr1(@RequestJson Student students){
        System.out.println(students);
    }


    /**
     * 请求路径 http://127.0.0.1:8080/test2 提交类型为application/json
     * 测试参数{"name":"张三"}
     * @param name
     * @param age
     */
    @RequestMapping(value = "/test2",method = RequestMethod.POST)
    public void testJsonStr2(@RequestJson String name,@RequestJson(defaultValue = "12") Integer age){
        System.out.println("name="+name+",age="+age);
    }

    /**
     * 请求路径 http://127.0.0.1:8080/test3 提交类型为application/json
     * 测试参数{"sid":1,"stuName":"里斯"}
     * @param students
     */
    @RequestMapping(value = "/test3",method = RequestMethod.POST)
    public void testJsonStr1(@RequestJson Map<String,Object> students){
        System.out.println(JsonUtil.convertBeanToStr(students));
    }

    @RequestMapping(value = "/test4",method = RequestMethod.POST)
    public void testJsonStr1(@RequestJson(elementType = Student.class) List<Student> students){
        Student student = students.get(0);
        System.out.println(student);
    }
}
