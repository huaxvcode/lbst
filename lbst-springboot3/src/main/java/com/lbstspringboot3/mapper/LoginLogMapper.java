package com.lbstspringboot3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lbstspringboot3.entity.LoginLog;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {
	IPage<Map<String, Object>> getLoginLogList(IPage<Map<String, Object>> page);
}
