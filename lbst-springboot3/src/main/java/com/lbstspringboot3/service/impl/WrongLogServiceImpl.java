package com.lbstspringboot3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbstspringboot3.entity.WrongLog;
import com.lbstspringboot3.mapper.WrongLogMapper;
import com.lbstspringboot3.service.IWrongLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
@Service
public class WrongLogServiceImpl extends ServiceImpl<WrongLogMapper, WrongLog> implements IWrongLogService {
	@Autowired
	private WrongLogMapper wrongLogMapper;


	@Override
	public List<WrongLog> getErrorLogList(Integer pageNum, Integer pageSize) {
		LambdaQueryWrapper<WrongLog> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByDesc(WrongLog::getTime);
		List<WrongLog> list = wrongLogMapper.selectList(wrapper);
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
	}
}
