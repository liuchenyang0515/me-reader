package com.me.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.me.reader.entity.Evaluation;
// 不用写mapper.xml
public interface EvaluationMapper extends BaseMapper<Evaluation> {
    public void changeState(Long evaluationId, String state);
}
