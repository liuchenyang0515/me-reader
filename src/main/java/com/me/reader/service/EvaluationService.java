package com.me.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.me.reader.entity.Evaluation;

import java.util.Date;
import java.util.List;

public interface EvaluationService {
    /**
     * 按图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);

    /**
     * 评论管理分页
     * @param page 评论列表页码
     * @param rows 每页数据行数
     * @return 分页对象
     */
    public IPage<Evaluation> paging(Integer page, Integer rows);
}
