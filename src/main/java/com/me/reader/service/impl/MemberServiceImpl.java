package com.me.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.me.reader.entity.Member;
import com.me.reader.mapper.MemberMapper;
import com.me.reader.service.MemberService;
import com.me.reader.service.exception.BussinessException;
import com.me.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService") // beanId与接口保持一致
@Transactional // 打开事务
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    /**
     * 会员注册
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        // 判断用户名是否已存在
        if (memberList.size() > 0) {
            throw new BussinessException("M01", "用户名已存在");
        }
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        int salt = new Random().nextInt(1000) + 1000; // 盐值，1000~1999之间随机数
        String md5 = MD5Utils.md5Digest(password, salt);
        member.setPassword(md5);
        member.setSalt(salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }
}
