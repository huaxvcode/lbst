package com.lbstspringboot3.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.lbstspringboot3.entity.User;
import com.lbstspringboot3.service.IUserService;
import com.lbstspringboot3.utils.RedisUtil;
import com.lbstspringboot3.utils.UUIDUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {

	@Autowired
	RedisUtil redisUtil;

	@Autowired
	IUserService userService;

	@GetMapping("/captcha-img")
	public void getCaptchaImg(HttpServletResponse response) throws IOException {
		// 定义图形验证码的长、宽、验证码字符数、干扰元素个数
		CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);

		response.setContentType("image/png");

		// 图形验证码写出，可以写出到文件，也可以写出到流
		captcha.write(response.getOutputStream());

		// 获取验证码
		String code = captcha.getCode();

		// 给前端生成key
		String key = UUIDUtil.getUUIDStringWithoutLine();

		// 设置过期时间 5 分钟
		//redisUtil.set(key, code, 60 * 5);

		// 设置过期时间 1 天
		redisUtil.set(key, code, 60 * 60 * 24);

		// 设置响应头中包含 key
		response.setHeader("Captcha-Key", key);
	}

	@PostMapping("/login")
	public SaResult login(@RequestBody Map<String, String> params) throws IOException {
		if (!params.containsKey("username") || !params.containsKey("password")) {
			return SaResult.error("请输入用户名和密码");
		}
		if (!params.containsKey("key") || !params.containsKey("code")) {
			return SaResult.error("请输入验证码");
		}

		User user = new User();
		user.setUsername(params.get("username"));
		user.setPassword(params.get("password"));

		String key = params.get("key");
		String code = params.get("code").toLowerCase();

		if (!redisUtil.hasKey(key)) {
			return SaResult.error("请输入验证码").setData(Map.of("status", 3));
		}

		String captcha = ((String) redisUtil.get(key)).toLowerCase();

		if (!code.equals(captcha)) {
			return SaResult.error("请输入正确的验证码").setData(Map.of("status", 2));
		}

		short status = userService.checkLogin(user);

		if (status == 1) {
			return SaResult.ok("登录成功").setData(Map.of("token", StpUtil.getTokenInfo(),
			                                              "status", 0));
		} else if (status == 2) {
			return SaResult.ok("无需重复登录").setData(Map.of("token", StpUtil.getTokenInfo(),
			                                                  "status", 0));
		} else {
			return SaResult.error("用户名或密码错误").setData(Map.of("status", 1));
		}
	}

	@PostMapping("/reg")
	public SaResult reg(@RequestBody Map<String, String> params) throws IOException {
		if (!params.containsKey("username") || !params.containsKey("password")) {
			return SaResult.error("请输入用户名和密码");
		}
		if (!params.containsKey("key") || !params.containsKey("code")) {
			return SaResult.error("请输入验证码");
		}

		User user = new User();
		user.setUsername(params.get("username"));
		user.setPassword(params.get("password"));

		String key = params.get("key");
		String code = params.get("code");

		if (!redisUtil.hasKey(key)) {
			return SaResult.error("请输入验证码").setData(4);
		}

		if (!code.equals(redisUtil.get(key))) {
			return SaResult.error("请输入正确的验证码").setData(3);
		}

		short status = userService.reg(user);

		if (status == 1) {
			return SaResult.ok("注册成功").setData(0);
		} else if (status == 2) {
			return SaResult.error("用户名已被占用").setData(2);
		} else {
			return SaResult.error("用户名或者密码的格式不正确").setData(1);
		}
	}

	@GetMapping("/logout")
	public SaResult logout() {
		StpUtil.logout();
		return SaResult.ok("登出成功");
	}
}
