package com.lbstspringboot3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lbstspringboot3.entity.BookLog;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
public interface BookLogMapper extends BaseMapper<BookLog> {

	IPage<Map<String, Object>> getBookLogList(IPage<Map<String, Object>> page);
}
