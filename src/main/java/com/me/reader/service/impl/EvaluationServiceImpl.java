package com.me.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("state", "enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        // 查询每个评论对应的会员和图书信息
        for (Evaluation eva : evaluationList) {
            Member member = memberMapper.selectById(eva.getMemberId()); // 通过evaluation表的member_id属性建立与member表的关联
            eva.setMember(member); // 放入手动添加的会员属性中，从而建立评价和会员的关联
            eva.setBook(book); // 从评论建立对应图书信息
        }
        return evaluationList;
    }
}
