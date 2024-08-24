package com.lbstspringboot3.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lbstspringboot3.entity.Book;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
public interface BookMapper extends BaseMapper<Book> {

  /**
   * 多表联结查询，分页查询
   */
  IPage<Map<String, Object>> getBookList(IPage<Map<String, Object>> page,
                                         String keyWord, Integer bookTypeId);
}
