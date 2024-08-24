package com.lbstspringboot3.service.impl;

import com.lbstspringboot3.entity.LoginLog;
import com.lbstspringboot3.mapper.LoginLogMapper;
import com.lbstspringboot3.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}
