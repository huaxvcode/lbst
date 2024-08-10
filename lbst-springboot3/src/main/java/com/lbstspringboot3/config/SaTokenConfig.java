package com.lbstspringboot3.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

	/**
	 * 注册 [Sa-Token 全局过滤器]
	 */
	@Bean
	public SaServletFilter getSaServletFilter() {
		return new SaServletFilter()

				// 指定 [拦截路由] 与 [放行路由]
				.addInclude("/**").addExclude("/favicon.ico")

				// 认证函数: 每次请求执行
				// 拦截器
				.setAuth(obj -> {
					System.out.println("---------- 进入Sa-Token全局认证 -----------");

					// 指定一条 match 规则
					SaRouter
							.match("/**") // 拦截的 path 列表，可以写多个
							.notMatch("/login", "/captcha-img", "/reg", "/check-captcha", "/check-login-status") // 排除掉的path
							.check(r -> StpUtil.checkLogin()); // 要执行的校验动作，可以写完整的 lambda 表达式

					// //// 根据路由划分模块，不同模块不同鉴权
					// //SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
					// //SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));
					// //SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
					// //SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
					// //SaRouter.match("/notice/**", r -> StpUtil.checkPermission("notice"));
					// //SaRouter.match("/comment/**", r -> StpUtil.checkPermission("comment"));
					//
				})

				// 异常处理函数：每次认证函数发生异常时执行此函数
				.setError(e -> {
					System.out.println("---------- 进入Sa-Token异常处理 -----------");

					return SaResult.error(e.getMessage());
				})

				// 前置函数：在每次认证函数之前执行
				.setBeforeAuth(obj -> {
					System.out.println("---------- 进入Sa-Token前置函数 -----------");

					SaHolder.getResponse()
							// ---------- 设置跨域响应头 ----------
							// 允许跨域的域名
							.setHeader("Access-Control-Allow-Origin", "*")
							// 允许的请求方式
							.setHeader("Access-Control-Allow-Methods", "*")
							// 允许的请求头
							.setHeader("Access-Control-Allow-Headers", "*")
							// 允许的响应头
							.setHeader("Access-Control-Expose-Headers", "*")
							// 有效时间
							.setHeader("Access-Control-Max-Age", "3600");

					// 如果是预检请求，则立即返回到前端
					SaRouter.match(SaHttpMethod.OPTIONS)
							.free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
							.back();
				});
	}
}
