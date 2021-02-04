package com.me.reader.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.me.reader.entity.Book;
import com.me.reader.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BookServiceImplTest {
    @Resource
    private BookService bookService;

    /**
     * 11:07:00 DEBUG [main] c.m.r.m.B.selectPage_mpCount - ==>  Preparing: SELECT COUNT(*) FROM book
     * 11:07:00 DEBUG [main] c.m.r.m.B.selectPage_mpCount - ==> Parameters:
     * 11:07:00 DEBUG [main] c.m.r.m.B.selectPage_mpCount - <==      Total: 1
     * 11:07:00 DEBUG [main] c.m.r.m.BookMapper.selectPage - ==>  Preparing: SELECT book_id,book_name,sub_title,author,cover,description,category_id,
     * evaluation_score,evaluation_quantity FROM book LIMIT ?
     * 11:07:00 DEBUG [main] c.m.r.m.BookMapper.selectPage - ==> Parameters: 10(Long)
     * 11:07:00 DEBUG [main] c.m.r.m.BookMapper.selectPage - <==      Total: 10
     *
     * ================接口更改新增category_id和order参数后==============
     * 14:28:08 DEBUG [main] c.m.r.m.B.selectPage_mpCount - ==>  Preparing: SELECT COUNT(*) FROM book WHERE (category_id = ?)
     * 14:28:08 DEBUG [main] c.m.r.m.B.selectPage_mpCount - ==> Parameters: 2(Long)
     * 14:28:08 DEBUG [main] c.m.r.m.B.selectPage_mpCount - <==      Total: 1
     * 14:28:08 DEBUG [main] c.m.r.m.BookMapper.selectPage - ==>  Preparing: SELECT book_id,book_name,sub_title,author,cover,description,category_id,
     * evaluation_score,evaluation_quantity FROM book WHERE (category_id = ?) ORDER BY evaluation_quantity DESC LIMIT ?
     * 14:28:08 DEBUG [main] c.m.r.m.BookMapper.selectPage - ==> Parameters: 2(Long), 10(Long)
     * 14:28:08 DEBUG [main] c.m.r.m.BookMapper.selectPage - <==      Total: 10
     *
     */
    @Test
    public void paging() {
        IPage<Book> pageObject = bookService.paging(2L, "quantity", 1, 10);
        List<Book> records = pageObject.getRecords();
        for (Book b : records) {
            System.out.println(b.getBookId() + ":" + b.getBookName());
        }
        System.out.println("总页数：" + pageObject.getPages());
        System.out.println("总记录数：" + pageObject.getTotal());
    }
}