package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.User;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-06
 */
public interface IUserService extends IService<User> {

	/**
	 * 根据用户名获取 user 实体
	 *
	 * @param username
	 * @return
	 */
	User getByUsername(String username);

	/**
	 * 用户登录
	 *
	 * @param user
	 * @return 0->登录失败、1->登录成功、2->已经登录
	 */
	short checkLogin(User user);

	/**
	 * 用户注册
	 *
	 * @param user
	 * @return 0->注册失败、1->注册成功、2->用户名已被占用
	 */
	short reg(User user);

	String getUsername(Integer id);

	List<Map<String, Object>> getUserList();
}
