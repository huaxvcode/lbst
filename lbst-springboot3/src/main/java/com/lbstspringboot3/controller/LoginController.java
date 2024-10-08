package com.lbstspringboot3.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.lbstspringboot3.common.Redis;
import com.lbstspringboot3.entity.User;
import com.lbstspringboot3.service.ILoginLogService;
import com.lbstspringboot3.service.IUserService;
import com.lbstspringboot3.utils.IpAddressUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

	@Autowired
	private Redis redis;

	@Autowired
	private IUserService userService;

	@Autowired
	private ILoginLogService loginLogService;

	@PostMapping("/login")
	public SaResult login(@RequestBody Map<String, String> params, HttpServletRequest httpServletRequest) throws IOException {
		if (!params.containsKey("username") || !params.containsKey("password")) {
			return SaResult.error("用户名或密码缺失").setData(Map.of("status", 1));
		}
		if (!params.containsKey("key") || !params.containsKey("code")) {
			return SaResult.error("验证码缺失").setData(Map.of("status", 5));
		}

		User user = new User();
		user.setUsername(params.get("username"));
		user.setPassword(params.get("password"));

		String key = params.get("key");
		String code = params.get("code").toLowerCase();

		if (!redis.hasKey(key)) {
			return SaResult.error("无效key").setData(Map.of("status", 4));
		}

		String captcha = ((String) redis.get(key)).toLowerCase();

		if (!code.equals(captcha)) {
			return SaResult.error("验证码错误").setData(Map.of("status", 6));
		}

		short status = userService.checkLogin(user);

		if (status == 1) {
			loginLogService.saveLoginLog(StpUtil.getLoginIdAsInt(),
			                             IpAddressUtil.getClientIpAddress(httpServletRequest), "用户执行登录操作");
			return SaResult.ok("登录成功").setData(Map.of("token", StpUtil.getTokenInfo(),
			                                              "status", 0));
		} else if (status == 2) {
			return SaResult.ok("重复登录").setData(Map.of("token", StpUtil.getTokenInfo(),
			                                              "status", 3));
		} else {
			return SaResult.error("用户名或密码错误").setData(Map.of("status", 2));
		}
	}

	@PostMapping("/reg")
	public SaResult reg(@RequestBody Map<String, String> params, HttpServletRequest httpServletRequest) throws IOException {
		if (!params.containsKey("username") || !params.containsKey("password")) {
			return SaResult.error("用户名或密码缺失").setData(2);
		}
		if (!params.containsKey("key") || !params.containsKey("code")) {
			return SaResult.error("验证码缺失").setData(5);
		}

		User user = new User();
		user.setUsername(params.get("username"));
		user.setPassword(params.get("password"));

		String key = params.get("key");
		String code = params.get("code").toLowerCase();

		if (!redis.hasKey(key)) {
			return SaResult.error("无效key").setData(4);
		}

		String captcha = ((String) redis.get(key)).toLowerCase();

		if (!code.equals(captcha)) {
			return SaResult.error("验证码错误").setData(6);
		}

		short status = userService.reg(user);

		if (status == 1) {
			loginLogService.saveLoginLog(userService.getByUsername(user.getUsername()).getId(),
			                             IpAddressUtil.getClientIpAddress(httpServletRequest), "用户执行注册操作");
			return SaResult.ok("注册成功").setData(0);
		} else if (status == 2) {
			return SaResult.error("用户名已被占用").setData(1);
		} else {
			return SaResult.error("用户名或者密码的格式不正确").setData(3);
		}
	}

	@GetMapping("/logout")
	public SaResult logout(HttpServletRequest httpServletRequest) {
		loginLogService.saveLoginLog(StpUtil.getLoginIdAsInt(),
		                             IpAddressUtil.getClientIpAddress(httpServletRequest), "用户执行登出操作");
		StpUtil.logout();
		log.info("登出成功！");
		return SaResult.ok("登出成功");
	}

	@GetMapping("/check-login-status")
	public SaResult checkLoginStatus() {
		return SaResult.ok().setData(StpUtil.isLogin());
	}

}
