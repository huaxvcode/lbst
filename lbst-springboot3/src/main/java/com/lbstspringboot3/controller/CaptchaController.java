package com.lbstspringboot3.controller;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;

import com.lbstspringboot3.common.Redis;
import com.lbstspringboot3.utils.UUIDUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class CaptchaController {

	@Autowired
	Redis redisUtil;

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

	@PostMapping("/check-captcha")
	public SaResult checkCaptcha(@RequestBody Map<String, String> data) {
		if (data.containsKey("code") && data.containsKey("key")) {
			String code = data.get("code");
			String key = data.get("key");

			if (!redisUtil.hasKey(key)) {
				return SaResult.error("无效 key").setData(1);
			}

			String captcha = ((String) redisUtil.get(key)).toLowerCase();

			if (!code.equals(captcha)) {
				return SaResult.error("验证码错误").setData(3);
			}

			return SaResult.ok("验证码正确").setData(0);
		}
		return SaResult.error("验证码或key缺失").setData(2);
	}
}
