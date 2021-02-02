package com.me.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test") // 说明实体对应哪一张表
public class Test {
    @TableId(type = IdType.AUTO) // 放在对应主键属性上，type = IdType.AUTO是利用数据库底层的自增主键插入操作
    @TableField("id") // 说明属性对应哪个字段
    private Integer id;
    // @TableField("content") // 如果字段名与属性名相同或者符合驼峰命名转换规则(比如数据库art_content，实体属性artContent)，则TableField可省略
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
