package com.qm.jsondemo.demo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiumin
 * @create 2018/12/24 18:23
 * @desc
 **/
@RestController
public class JsonController {

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void testJsonStr(@RequestBody String str){
        System.out.println(str);
    }


}
