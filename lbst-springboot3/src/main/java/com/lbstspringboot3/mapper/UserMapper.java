package com.lbstspringboot3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lbstspringboot3.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 林日清
 * @since 2024-08-06
 */
public interface UserMapper extends BaseMapper<User> {

	List<Map<String, Object>> getUserList();
}
