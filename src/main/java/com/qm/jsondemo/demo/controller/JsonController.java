package com.qm.jsondemo.demo.controller;

import com.qm.jsondemo.demo.handler.RequestJson;
import com.qm.jsondemo.demo.model.Classes;
import com.qm.jsondemo.demo.model.Student;
import com.qm.jsondemo.demo.util.JsonUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author qiumin
 **/
@RestController
public class JsonController {


    /**
     * 请求路径 http://127.0.0.1:8080/test 提交类型为application/json
     * 测试参数{"sid":1,"stuName":"里斯"}
     * @param str
     */
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void testJsonStr(@RequestBody(required = false) String str){
        System.out.println(str);
    }
    /**
     * 请求路径 http://127.0.0.1:8080/testAcceptOrdinaryParam?str=123
     * 测试参数
     * @param str
     */
    @RequestMapping(value = "/testAcceptOrdinaryParam",method = {RequestMethod.GET,RequestMethod.POST})
    public void testAcceptOrdinaryParam(String str){
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
     * 请求路径 http://127.0.0.1:8080/test2?sex=男 提交类型为application/json post
     * 测试参数{"name":"张三"}
     * @param name
     * @param age
     */
    @RequestMapping(value = "/test2",method = RequestMethod.POST)
    public void testJsonStr2(@RequestJson String name,@RequestJson(defaultValue = "12") Integer age,String sex){
        System.out.println("name="+name+",age="+age+",sex="+sex);
        // 打印结果 name=张三,age=12,sex=男
    }

    /**
     * 请求路径 http://127.0.0.1:8080/test3 提交类型为application/json
     * 测试参数{"sid":1,"stuName":"里斯"}
     * 自定义的不会被拦截,map参数会被spring 自身的mapMethodProcess方法给拦截
     * @param students
     */
    @RequestMapping(value = "/test3",method = RequestMethod.POST)
    public void testJsonStr3(@RequestJson Map<String,Object> students){
        System.out.println(JsonUtil.convertBeanToStr(students));
    }

    /**
     * 请求路径 http://127.0.0.1:8080/test4 提交类型为application/json
     * 测试参数：[{"sid":1,"stuName":"里斯"},{"sid":2,"stuName":"里斯222"}]
     * @param students
     */
    @RequestMapping(value = "/test4",method = RequestMethod.POST)
    public void testJsonStr4(@RequestBody List<Student> students){
        Student student = students.get(0);
        System.out.println(student);
    }

    /**
     * 请求路径 http://127.0.0.1:8080/test5   提交类型为application/json
     * 测试参数：{"cid":2,"classesName":"一班","studentList":[{"sid":1,"stuName":"张三"},{"sid":1,"stuName":"李四"},{"sid":3,"stuName":"王五"}]}
     * @param classes
     */
    @RequestMapping(value = "/test5",method = RequestMethod.POST)
    public void testJsonStr5(@RequestJson Classes classes){
        System.out.println(classes);

    }


    /**
     * 请求路径 http://127.0.0.1:8080/test6   提交类型为application/json
     * 测试参数：[{"cid":1,"classesName":"一班","studentList":[{"sid":1,"stuName":"张三"},{"sid":2,"stuName":"李四"}]},{"cid":2,"classesName":"二班","studentList":[{"sid":1,"stuName":"张三2"},{"sid":2,"stuName":"李四2"}]}]
     * @param classes
     */
    @RequestMapping(value = "/test6",method = RequestMethod.POST)
    public void testJsonStr6(@RequestJson List<Classes> classes){
        System.out.println(classes);

    }

    /**
     * 请求路径 http://127.0.0.1:8080/test7   提交类型为application/json
     * 测试参数：{"time":"2018-12-22 10:56:12"}
     * @param time
     */
    @RequestMapping(value = "/test7",method = RequestMethod.POST)
    public void testJsonStr7(@RequestJson Date time){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));
    }


    /*
     * 前端传Json参数 {"name":"张三"}
     * 请求路径 http://127.0.0.1:8080/XXX?sex=男 提交方式post
     * 后端使用
     *     public String testAcceptJsonParam(@RequestJson String name,@RequestJson(defaultValue="12") Integer age,String sex)
     *
     * 后端需打印正确结果  即name=张三，age=12,sex=男
     *
     * */

}
