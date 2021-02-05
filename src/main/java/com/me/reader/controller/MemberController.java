package com.me.reader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @GetMapping("/register.html") // .html可加可不加，但是加上对于搜索引擎的抓取是很友好的
    public ModelAndView showRegister() {
        return new ModelAndView("/register");
    }

    @PostMapping("/registe")
    @ResponseBody
    // 如何获取当前会话session？SpringMVC技巧：想要获得原生对象HttpServletRequest对象，直接加上参数即可
    public Map registe(String vc, String username, String password, String nickname, HttpServletRequest request) {
        // 正确验证码
        String verifyCode = (String)request.getSession().getAttribute("kaptchaVerifyCode");
        // 验证码对比
        Map<String, String> result = new HashMap<>();
        if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            result.put("code", "0");
            result.put("msg", "success");
        }
        return result;
    }
}
