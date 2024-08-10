package com.lbstspringboot3.common;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
	// 密码加密
	public String encode(String rawPassword) {
		return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
	}

	// 密码校验
	public boolean matches(String rawPassword, String encodedPassword) {
		return BCrypt.checkpw(rawPassword, encodedPassword);
	}
}
