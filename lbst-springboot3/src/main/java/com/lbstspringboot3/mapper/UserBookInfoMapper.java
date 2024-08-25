package com.lbstspringboot3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lbstspringboot3.entity.UserBookInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
public interface UserBookInfoMapper extends BaseMapper<UserBookInfo> {

	List<Map<String, Object>> getBorrowListByUid(Integer uid);
}
