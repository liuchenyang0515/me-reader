package com.me.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.me.reader.entity.Book;
import com.me.reader.entity.Evaluation;
import com.me.reader.entity.Member;
import com.me.reader.mapper.BookMapper;
import com.me.reader.mapper.EvaluationMapper;
import com.me.reader.mapper.MemberMapper;
import com.me.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;

    /**
     * 按图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId); // 按照书的id查询这本书的评论
        queryWrapper.eq("state", "enable"); // 有效的评论
        queryWrapper.orderByDesc("create_time"); // 按照创建时间降序排列
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);// 查询到满足条件的所有评论
        // 查询每个评论对应的会员和图书信息
        for (Evaluation eva : evaluationList) {
            Member member = memberMapper.selectById(eva.getMemberId()); // 通过evaluation表的member_id属性建立与member表的关联
            // 后续需要显示是谁评论的，但是evaluation表只有member的id，我们要通过member的id去查到这个昵称
            // 所以需要从一个表evaluation表的member_id属性查另一个member表(member_id是主键)的nickname属性，一对一的关系
            eva.setMember(member); // 放入手动添加的会员属性中，从而建立评价和会员的关联
            eva.setBook(book); // 从评论建立对应图书信息
        }
        return evaluationList;
    }

    /**
     * 评论管理分页
     *
     * @param page       评论列表页码
     * @param rows       每页数据行数
     * @return 分页对象
     */
    @Override
    public IPage<Evaluation> paging(Integer page, Integer rows) {
        Page<Evaluation> p = new Page<>(page, rows);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        IPage<Evaluation> pageObject = evaluationMapper.selectPage(p, queryWrapper);
        return pageObject;
    }
}
