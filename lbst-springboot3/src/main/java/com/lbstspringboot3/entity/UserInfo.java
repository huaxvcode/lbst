package com.lbstspringboot3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 林日清
 * @since 2024-08-10
 */
@Getter
@Setter
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息主键 id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id（外键）
     */
    private Integer uid;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String headImg;

    /**
     * 出生年份
     */
    private LocalDate yearOfBirth;

    /**
     * 学历 0-小学、1-初中、2-高中、3-大学、4-硕士、5-博士、6-博士后
     */
    private Integer education;
}
