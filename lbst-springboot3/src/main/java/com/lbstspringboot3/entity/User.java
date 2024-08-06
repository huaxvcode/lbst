package com.lbstspringboot3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author 林日清
 * @since 2024-08-06
 */
@Getter
@Setter
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户 id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 用户密码
	 */
	private String password;
}
