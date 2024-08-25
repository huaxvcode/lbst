package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.Role;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-18
 */
public interface IRoleService extends IService<Role> {
	/**
	 * 通过 uid 获取用户角色类型
	 *
	 * @param uid 用户 id
	 * @return 返回一个整数，0-超级管理员、1-管理员、2-普通用户，默认是 2
	 */
	Integer getRoleByUid(Integer uid);

	Boolean setRoleType(Integer uid, Integer type);
}
