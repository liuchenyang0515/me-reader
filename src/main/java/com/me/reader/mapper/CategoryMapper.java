package com.me.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.me.reader.entity.Category;

/*图书分类Mapper接口*/
// Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能，所以可以删除resources/mappers/category.xml也可以达到同样的效果
public interface CategoryMapper extends BaseMapper<Category> {
}
