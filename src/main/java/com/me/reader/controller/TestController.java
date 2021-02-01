package com.me.reader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {
    @GetMapping("/test/t1")
    // 这里会通过freemarker模版,后面不需要写.ftl，因为配置里面写了<property name="suffix" value=".ftl"/>
    public ModelAndView test1() {
        // ModelAndView里设置模版引擎的类型
        // 设置了<property name="contentType" value="text/html;charset=utf-8"/>，返回的中文
        // 设置了<property name="templateLoaderPath" value="/WEB-INF/ftl"/>脚本目录，只需要输入文件名即可，不需要带后缀，ModelAndView默认请求转发到test.ftl
        return new ModelAndView("/test");
    }

    @GetMapping("/test/t2")
    @ResponseBody // 直接返回文本，不通过模版，<mvc:message-converters>设置了<value>text/html;charset=utf-8</value>，返回的中文
    public Map<String, String> test2() {
        Map<String, String> result = new HashMap<>();
        result.put("test", "测试文本");
        return result;
    }
}
