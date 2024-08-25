package com.lbstspringboot3.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbstspringboot3.common.PasswordEncoder;
import com.lbstspringboot3.entity.User;
import com.lbstspringboot3.mapper.UserMapper;
import com.lbstspringboot3.service.IUserService;
import com.lbstspringboot3.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-06
 */
@Service
public class UserServiceImpl
		extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User getByUsername(String username) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		return userMapper.selectOne(queryWrapper);
	}

	@Override
	public short checkLogin(User user) {
		User user_ = this.getByUsername(user.getUsername());
		if (user_ != null &&
		    passwordEncoder.matches(user.getPassword(), user_.getPassword())) {
			// 在这里集成了登录
			if (StpUtil.isLogin()) {
				return 2;
			}

			StpUtil.login(user_.getId());
			user.setId(user_.getId());
			return 1;
		}
		return 0;
	}

	@Override
	public short reg(User user) {
		User user_ = this.getByUsername(user.getUsername());
		if (user_ != null)
			return 2;
		if (UserValidator.isValidUsername(user.getUsername()) &&
		    UserValidator.isValidPassword(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userMapper.insert(user);
			return 1;
		}
		return 0;
	}

	@Override
	public String getUsername(Integer id) {
		User user = this.getById(id);
		if (user == null)
			return null;
		return user.getUsername();
	}

	@Override
	public List<Map<String, Object>> getUserList() {
		return userMapper.getUserList();
	}
}
