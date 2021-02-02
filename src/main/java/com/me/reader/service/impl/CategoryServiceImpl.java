package com.me.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.me.reader.entity.Category;
import com.me.reader.mapper.CategoryMapper;
import com.me.reader.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 开发流程：先实体类，再Mapper接口，[该步骤可选，不写对应Mapper接口xml文件也能实现相同功能]接着对应Mapper接口的xml文件(到这里数据交互的代码就完成了),
 * 然后推进到service决定查询还是写入，最后controller调用service，
 * 调用成功后将查询结果放入请求与模版引擎组合，组合渲染形成html
 */
@Service("categoryService") // beanId，符合面向接口编程的规则，测试类里会用到的
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true) // 只读，默认不需要事务
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有图书分类
     *
     * @return 图书分类List
     */
    @Override
    public List<Category> selectAll() {
        List<Category> list = categoryMapper.selectList(new QueryWrapper<Category>());// 查询所有数据，直接传QueryWrapper对象
        return list;
    }
}
