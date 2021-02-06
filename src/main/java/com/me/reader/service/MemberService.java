package com.me.reader.service;

import com.me.reader.entity.Member;
import com.me.reader.entity.MemberReadState;

public interface MemberService {
    /**
     * 会员注册
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    public Member createMember(String username, String password, String nickname);

    /**
     * 登录检查
     * @param username 用户名
     * @param password 密码
     * @return  登录对象
     */
    public Member checkLogin(String username, String password);

    /**
     * 获取阅读状态
     * @param memberId 会员编号
     * @param bookId 图书编号
     * @return 阅读状态对象
     */
    public MemberReadState selectMemberReadState(Long memberId, Long bookId);
}