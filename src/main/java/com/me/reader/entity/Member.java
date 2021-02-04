package com.me.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.tools.JavaCompiler;
import java.sql.Date;

/**
 * 由于数据库8版本存在细微差异，member被选为保留字段，建议在表名上加上反引号，避免保留字段和表名冲突，如:
 * @TableName("`member`")
 */
@TableName("`member`")
public class Member {
    @TableId(type = IdType.AUTO)
    private Long memberId;
    private String username;
    private String password;
    private Integer salt;
    private String nickname;
    private Date createTime;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSalt() {
        return salt;
    }

    public void setSalt(Integer salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = new Date(createTime.getTime());
    }
}
