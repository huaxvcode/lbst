package com.lbstspringboot3.service.impl;

import com.lbstspringboot3.entity.User;
import com.lbstspringboot3.mapper.UserMapper;
import com.lbstspringboot3.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
