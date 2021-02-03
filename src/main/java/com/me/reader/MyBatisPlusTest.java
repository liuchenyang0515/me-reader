package com.me.reader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.me.reader.entity.Test;
import com.me.reader.mapper.TestMapper;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MyBatisPlusTest {
    @Resource
    private TestMapper testMapper;

    /**
     * 10:02:27 DEBUG [main] c.m.r.mapper.TestMapper.insert - ==>  Preparing: INSERT INTO test ( content ) VALUES ( ? )
     * 10:02:27 DEBUG [main] c.m.r.mapper.TestMapper.insert - ==> Parameters: MyBatis Plus测试(String)
     * 10:02:27 DEBUG [main] c.m.r.mapper.TestMapper.insert - <==    Updates: 1
     *
     * 看源码BaseMapper源码可知，insert只能创建一个实体对象再插入
     */
    @org.junit.Test
    public void testInsert() {
        Test test = new Test();
        test.setContent("MyBatis Plus测试");
        // 根据配置好的映射关系，自动生成sql语句，大大减少工作量
        testMapper.insert(test);
    }

    /**
     * 15:36:34 DEBUG [main] c.m.r.m.TestMapper.updateById - ==>  Preparing: UPDATE test SET content=? WHERE id=?
     * 15:36:34 DEBUG [main] c.m.r.m.TestMapper.updateById - ==> Parameters: MyBatis Plus测试1(String), 9(Integer)
     * 15:36:34 DEBUG [main] c.m.r.m.TestMapper.updateById - <==    Updates: 1
     *
     * 看源码BaseMapper源码可知，update可以创建实体对象或Wrapper更新
     */
    @org.junit.Test
    public void testUpdate() {
        Test test = testMapper.selectById(9); // 数据查看到上一个数据id是9
        test.setContent("MyBatis Plus测试1");
        testMapper.updateById(test);
    }

    /**
     * 15:39:53 DEBUG [main] c.m.r.m.TestMapper.deleteById - ==>  Preparing: DELETE FROM test WHERE id=?
     * 15:39:53 DEBUG [main] c.m.r.m.TestMapper.deleteById - ==> Parameters: 9(Integer)
     * 15:39:53 DEBUG [main] c.m.r.m.TestMapper.deleteById - <==    Updates: 1
     *
     * 看源码BaseMapper源码可知，delete可以根据id或者Wrapper删除
     */
    @org.junit.Test
    public void testDelete() {
        testMapper.deleteById(9);
    }

    /**
     * 15:42:28 DEBUG [main] c.m.r.m.TestMapper.selectList - ==>  Preparing: SELECT id,content FROM test WHERE (id = ?)
     * 15:42:28 DEBUG [main] c.m.r.m.TestMapper.selectList - ==> Parameters: 7(Integer)
     * 15:42:28 DEBUG [main] c.m.r.m.TestMapper.selectList - <==      Total: 1
     *
     *
     * 15:43:31 DEBUG [main] c.m.r.m.TestMapper.selectList - ==>  Preparing: SELECT id,content FROM test WHERE (id > ?)
     * 15:43:31 DEBUG [main] c.m.r.m.TestMapper.selectList - ==> Parameters: 5(Integer)
     * 15:43:31 DEBUG [main] c.m.r.m.TestMapper.selectList - <==      Total: 3
     *
     *
     * 15:45:31 DEBUG [main] c.m.r.m.TestMapper.selectList - ==>  Preparing: SELECT id,content FROM test WHERE (id = ? AND id > ?)
     * 15:45:31 DEBUG [main] c.m.r.m.TestMapper.selectList - ==> Parameters: 7(Integer), 5(Integer)
     * 15:45:31 DEBUG [main] c.m.r.m.TestMapper.selectList - <==      Total: 1
     *
     * 看源码BaseMapper源码可知，select可以根据id或Wrapper或者其他api支持的方法
     */
    @org.junit.Test
    public void testSelect() {
        QueryWrapper<Test> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 7); // 多个子句用AND连接
        queryWrapper.gt("id", 5); // 查询id大于5的数据
        List<Test> list = testMapper.selectList(queryWrapper);
        System.out.println(list.get(0));
    }
}
