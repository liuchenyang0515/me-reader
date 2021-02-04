package com.me.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.me.reader.entity.Book;
import com.me.reader.entity.Category;
import com.me.reader.entity.Evaluation;
import com.me.reader.service.BookService;
import com.me.reader.service.CategoryService;
import com.me.reader.service.EvaluationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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

    @GetMapping("/book/{id}") // springmvc的路径变量
    public ModelAndView showDetail(@PathVariable("id") Long id) {
        Book book = bookService.selectById(id);
        List<Evaluation> evaluationList = evaluationService.selectByBookId(id);
        ModelAndView mav = new ModelAndView("/detail"); // 请求转发跳转到detail页面
        mav.addObject("book", book);
        mav.addObject("evaluationList", evaluationList); // 评论列表
        return mav;
    }

}
