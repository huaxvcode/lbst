package com.lbstspringboot3.utils;

import java.util.regex.Pattern;

public class UserValidator {

	// 用户名规则：长度3-20个字符，允许字母、数字、下划线
	private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,20}$";

	// 密码规则：长度3-20个字符，必须包含大写字母、小写字母、数字和特殊字符
	private static final String PASSWORD_REGEX = "^[A-Za-z0-9_@$!%*?&#]{3,20}$";

	// 校验用户名格式
	public static boolean isValidUsername(String username) {
		return username != null && Pattern.matches(USERNAME_REGEX, username);
	}

	// 校验密码格式
	public static boolean isValidPassword(String password) {
		return password != null && Pattern.matches(PASSWORD_REGEX, password);
	}
}
