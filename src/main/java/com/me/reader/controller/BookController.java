package com.me.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.me.reader.entity.*;
import com.me.reader.service.BookService;
import com.me.reader.service.CategoryService;
import com.me.reader.service.EvaluationService;
import com.me.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/*url和方法的绑定*/
@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;

    @Resource
    private BookService bookService;

    @Resource
    private EvaluationService evaluationService;

    @Resource
    private MemberService memberService;

    /**
     * 显示首页
     *
     * @return
     */
    @GetMapping("/")
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView("/index"); // 不需要.ftl后缀
        List<Category> categoryList = categoryService.selectAll();
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    /**
     * 前端页面渲染完成后发出ajax请求，/books?p=1&categoryId=-1&order=quantity，该方法参数会自动匹配url的?之后的参数
     * 分页查询图书列表
     * @param p 页号
     * @return 分页对象
     */
    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Long categoryId, String order, Integer p) {
        if (p == null) p = 1;
        IPage<Book> pageObject = bookService.paging(categoryId, order, p, 10);
        // 打印一下，方便查看前端分页的时候，每次分页请求的数据
        List<Book> records = pageObject.getRecords();
        for (Book b : records) {
            System.out.println(b.getBookId() + ":" + b.getBookName());
        }

        return pageObject;
    }

    @GetMapping("/book/{id}") // springmvc的路径变量，这{id}是前端传来的bookId，<a href="/book/{{bookId}}" style="color: inherit">，点击后命中这个路由
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session) {
        Book book = bookService.selectById(id);
        List<Evaluation> evaluationList = evaluationService.selectByBookId(id); // 按照图书编号查询短评，需要除了book表之外的evaluation表的内容
        Member member = (Member) session.getAttribute("loginMember"); // 获取登录状态的会员，没登录就是null
        ModelAndView mav = new ModelAndView("/detail"); // 请求转发跳转到detail页面
        if (member != null) {
            // 获取会员阅读状态，可能为null，表示没点击"想看"或者"在看"
            MemberReadState memberReadState = memberService.selectMemberReadState(member.getMemberId(), id);
            mav.addObject("memberReadState", memberReadState);
        }
        mav.addObject("book", book); // 为了展示详情页上面版块，需要星星数，多少人评价，subTitle之类的参数
        mav.addObject("evaluationList", evaluationList); // 评论列表，需要时间、昵称、评论内容之类的参数
        return mav;
    }

}
