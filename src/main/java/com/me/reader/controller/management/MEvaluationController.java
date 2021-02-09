package com.me.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.me.reader.entity.Book;
import com.me.reader.entity.Evaluation;
import com.me.reader.entity.Member;
import com.me.reader.service.BookService;
import com.me.reader.service.EvaluationService;
import com.me.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private BookService bookService;
    @Resource
    private MemberService memberService;

    @GetMapping("/evaluations.html")
    public ModelAndView showEvaluationList() {
        return new ModelAndView("/management/evaluations");
    }

    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> list(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        IPage<Evaluation> pageObject = evaluationService.paging(page, limit);
        for(Evaluation e : pageObject.getRecords()) {
            Book book = bookService.selectById(e.getBookId());// evaluation对象有bookid，根据bookid获取book对象
            Member member = memberService.selectMemberById(e.getMemberId());
            e.setBook(book);
            e.setMember(member);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("book", pageObject.getRecords()); // 当前分页数据
        result.put("count", pageObject.getTotal()); // 未分页时记录总数
        return result;
    }

}
