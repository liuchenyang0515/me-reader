package com.me.reader.service.impl;

import com.me.reader.entity.Category;
import com.me.reader.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CategoryServiceImplTest {
    @Resource
    private CategoryService categoryService;

    /**
     * 16:58:30 DEBUG [main] c.m.r.m.C.selectList - ==>  Preparing: SELECT category_id,category_name FROM category
     * 16:58:30 DEBUG [main] c.m.r.m.C.selectList - ==> Parameters:
     * 16:58:30 DEBUG [main] c.m.r.m.C.selectList - <==      Total: 4
     */
    @Test
    public void selectAll() {
        List<Category> list = categoryService.selectAll();
        System.out.println(list);
    }
}