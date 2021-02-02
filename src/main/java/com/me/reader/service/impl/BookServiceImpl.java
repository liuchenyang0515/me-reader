package com.me.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.me.reader.entity.Book;
import com.me.reader.mapper.BookMapper;
import com.me.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    /**
     * 分页查询图书
     *
     * @param page 页号
     * @param rows 每页记录数
     * @return 分页对象
     */
    @Override
    public IPage<Book> paging(Integer page, Integer rows) {
        Page<Book> p = new Page<>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }
}
