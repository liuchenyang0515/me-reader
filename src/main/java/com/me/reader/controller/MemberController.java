package com.me.reader.controller;

import com.me.reader.entity.Evaluation;
import com.me.reader.entity.Member;
import com.me.reader.entity.MemberReadState;
import com.me.reader.service.MemberService;
import com.me.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {
    @Resource
    private MemberService memberService;

    @GetMapping("/register.html") // .html可加可不加，但是加上对于搜索引擎的抓取是很友好的
    public ModelAndView showRegister() {
        return new ModelAndView("/register");
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin() {
        return new ModelAndView("/login");
    }

    @PostMapping("/registe")
    @ResponseBody
    // 如何获取当前会话session？SpringMVC技巧：想要获得原生对象HttpServletRequest对象，直接加上参数即可
    public Map registe(String vc, String username, String password, String nickname, HttpServletRequest request) {
        // 正确验证码
        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
        // 验证码对比
        Map<String, String> result = new HashMap<>();
        if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            try {
                memberService.createMember(username, password, nickname);
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BussinessException ex) {
                ex.printStackTrace();
                result.put("code", ex.getCode());
                result.put("msg", ex.getMsg());
            }
        }
        return result;
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, String vc, HttpSession session) {
        // 正确验证码
        String verifyCode = (String) session.getAttribute("kaptchaVerifyCode");
        // 验证码对比
        Map<String, String> result = new HashMap<>();
        if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            try {
                Member member = memberService.checkLogin(username, password);
                session.setAttribute("loginMember", member); // 存入对象，方便前端获取右上角用户昵称
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BussinessException ex) {
                ex.printStackTrace();
                result.put("code", ex.getCode());
                result.put("msg", ex.getMsg());
            }
        }
        return result;
    }

    /**
     * 更新想看/看过阅读状态
     * @param memberId 会员id
     * @param bookId 图书id
     * @param readState 阅读状态
     * @return 处理结果
     */
    @PostMapping("/update_read_state")
    @ResponseBody
    public Map<String, String> updateReadState(Long memberId, Long bookId, Integer readState) {
        Map<String, String> result = new HashMap<>();
        try {
            MemberReadState memberReadState = memberService.updateMemberReadState(memberId, bookId, readState);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @PostMapping("/evaluate")
    @ResponseBody
    public Map<String, Object> evaluation(Long memberId, Long bookId, Integer score, String content) {
        Map<String, Object> result = new HashMap<>();
        try {
            Evaluation evaluate = memberService.evaluate(memberId, bookId, score, content);
            result.put("code", "0");
            result.put("msg", "success");
            result.put("evaluation", evaluate);
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }


    @PostMapping("/enjoy")
    @ResponseBody
    public Map enjoy(Long evaluationId) {
        Map result = new HashMap<>();
        try {
            Evaluation enjoy = memberService.enjoy(evaluationId);
            result.put("code", "0");
            result.put("msg", "success");
            result.put("enjoy", enjoy);
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }
}
