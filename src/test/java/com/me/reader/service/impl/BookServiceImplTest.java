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

    @Test
    public void paging() {
        IPage<Book> pageObject = bookService.paging(1, 10);
        List<Book> records = pageObject.getRecords();
        for (Book b : records) {
            System.out.println(b.getBookId() + ":" + b.getBookName());
        }
        System.out.println("总页数：" + pageObject.getPages());
        System.out.println("总记录数：" + pageObject.getTotal());
    }
}