package com.lbstspringboot3.service.impl;

import com.lbstspringboot3.entity.OpLog;
import com.lbstspringboot3.mapper.OpLogMapper;
import com.lbstspringboot3.service.IOpLogService;
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
public class OpLogServiceImpl extends ServiceImpl<OpLogMapper, OpLog> implements IOpLogService {

}
