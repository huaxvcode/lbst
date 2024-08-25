package com.lbstspringboot3.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.lbstspringboot3.entity.User;
import com.lbstspringboot3.service.IOpLogService;
import com.lbstspringboot3.service.IRoleService;
import com.lbstspringboot3.service.IUserService;
import com.lbstspringboot3.utils.IpAddressUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IOpLogService opLogService;

	@GetMapping("/username/{uid}")
	public SaResult getUsername(@PathVariable Integer uid) {
		return SaResult.ok().setData(userService.getUsername(uid));
	}

	@GetMapping("/role/{uid}")
	public SaResult getRoleByUid(@PathVariable Integer uid) {
		return SaResult.ok().setData(roleService.getRoleByUid(uid));
	}

	@GetMapping("/userList")
	public SaResult getUserList() {
		List<Map<String, Object>> list = userService.getUserList();
		if (list == null) {
			list = new ArrayList<>();
		}
		return SaResult.ok().setData(list);
	}

	@GetMapping("/setRoleType/{uid}/{type}")
	public SaResult setRoleType(@PathVariable Integer uid, @PathVariable Integer type, HttpServletRequest request) {
		if (!(type >= 0 && type <= 2)) return SaResult.error();
		User user = userService.getById(uid);
		if (user == null) return SaResult.error();
		opLogService.saveOpLog(StpUtil.getLoginIdAsInt(), IpAddressUtil.getClientIpAddress(request), String.format(
				"改变用户 %s 的角色权限为：%s", uid + "", type + ""));
		return SaResult.ok().setData(roleService.setRoleType(uid, type));
	}
}
