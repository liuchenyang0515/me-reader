package com.me.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.me.reader.entity.Book;

/**
 * 图书服务
 */
public interface BookService {
    /**
     * 分页查询图书
     * @param page 页号
     * @param rows 每页记录数
     * @return 分页对象
     */
    public IPage<Book> paging(Integer page, Integer rows);
}
