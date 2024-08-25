package com.lbstspringboot3.config;

import cn.dev33.satoken.stp.StpInterface;
import com.lbstspringboot3.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

	@Autowired
	private IRoleService roleService;

	/**
	 * 返回一个账号所拥有的权限码集合
	 */
	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		return new ArrayList<>();
	}

	/**
	 * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
	 */
	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		Integer roleType = roleService.getRoleByUid(Integer.parseInt(loginId + ""));
		List<String> list = List.of(roleType + "");
		return list;
	}

}
