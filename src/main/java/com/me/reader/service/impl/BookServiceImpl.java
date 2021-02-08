package com.me.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.me.reader.entity.Book;
import com.me.reader.entity.Evaluation;
import com.me.reader.entity.MemberReadState;
import com.me.reader.mapper.BookMapper;
import com.me.reader.mapper.EvaluationMapper;
import com.me.reader.mapper.MemberReadStateMapper;
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
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    /**
     * 分页查询图书
     *
     * @param categoryId 分类编号
     * @param order      排序方式
     * @param page       页号
     * @param rows       每页记录数
     * @return 分页对象
     */
    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows) {
        Page<Book> p = new Page<>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        // 前端是设置的隐藏域，点击分类后修改隐藏域的值来控制categoryId和order来控制分类和排序分类
        if (categoryId != null && categoryId != -1) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (order != null) {
            if (order.equals("quantity")) {
                queryWrapper.orderByDesc("evaluation_quantity"); // 按照热度--评分人数降序
            } else {
                queryWrapper.orderByDesc("evaluation_score"); // 按照评分降序
            }
        }
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }

    /**
     * 根据图书编号查询图书对象
     *
     * @param bookId 图书编号
     * @return 图书对象
     */
    @Override
    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    /**
     * 更新图书评分/评价数量
     */
    @Override
    @Transactional // 开启声明式事务
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    /**
     * 创建新的图书
     *
     * @param book
     * @return
     */
    @Override
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    /**
     * 更新图书
     *
     * @param book 新图书数据
     * @return 更新后的数据
     */
    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    /**
     * 删除图书及相关数据
     *
     * 19:47:47 DEBUG [http-nio-80-exec-7] c.m.r.m.BookMapper.deleteById - ==>  Preparing: DELETE FROM book WHERE book_id=?
     * 19:47:47 DEBUG [http-nio-80-exec-7] c.m.r.m.BookMapper.deleteById - ==> Parameters: 47(Long)
     * 19:47:47 DEBUG [http-nio-80-exec-7] c.m.r.m.BookMapper.deleteById - <==    Updates: 1
     * 19:47:47 DEBUG [http-nio-80-exec-7] o.m.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3925b814]
     * 19:47:47 DEBUG [http-nio-80-exec-7] o.m.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3925b814] from current transaction
     * 19:47:47 DEBUG [http-nio-80-exec-7] c.m.r.m.M.delete - ==>  Preparing: DELETE FROM member_read_state WHERE (book_id = ?)
     * 19:47:47 DEBUG [http-nio-80-exec-7] c.m.r.m.M.delete - ==> Parameters: 47(Long)
     * 19:47:47 DEBUG [http-nio-80-exec-7] c.m.r.m.M.delete - <==    Updates: 0
     * 19:47:47 DEBUG [http-nio-80-exec-7] o.m.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3925b814]
     * 19:47:47 DEBUG [http-nio-80-exec-7] o.m.spring.SqlSessionUtils - Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3925b814] from current transaction
     * 19:47:47 DEBUG [http-nio-80-exec-7] c.m.r.m.E.delete - ==>  Preparing: DELETE FROM evaluation WHERE (book_id = ?)
     * 19:47:47 DEBUG [http-nio-80-exec-7] c.m.r.m.E.delete - ==> Parameters: 47(Long)
     * 19:47:47 DEBUG [http-nio-80-exec-7] c.m.r.m.E.delete - <==    Updates: 0
     * @param bookId 图书编号
     */
    @Override
    @Transactional
    public void deleteBook(Long bookId) { // 三个表的删除，书籍、评论、会员对该书的阅读状态都得删除
        bookMapper.deleteById(bookId);
        QueryWrapper<MemberReadState> msrQueryWrapper = new QueryWrapper<>();
        msrQueryWrapper.eq("book_id", bookId);
        memberReadStateMapper.delete(msrQueryWrapper);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<>();
        evaluationQueryWrapper.eq("book_id", bookId);
        evaluationMapper.delete(evaluationQueryWrapper);
    }
}
