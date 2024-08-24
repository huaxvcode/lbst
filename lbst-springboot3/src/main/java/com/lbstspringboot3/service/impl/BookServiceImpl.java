package com.lbstspringboot3.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbstspringboot3.entity.Book;
import com.lbstspringboot3.entity.User;
import com.lbstspringboot3.mapper.BookMapper;
import com.lbstspringboot3.service.IBookService;
import com.lbstspringboot3.utils.TimeFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BookServiceImpl
    extends ServiceImpl<BookMapper, Book> implements IBookService {

  @Autowired BookMapper bookMapper;

  @Override
  public Map<String, Object> getBookList(Integer pageNum, Integer pageSize,
                                         String keyWord, Integer bookTypeId) {
    IPage<Map<String, Object>> page = new Page<>(pageNum, pageSize);
    IPage<Map<String, Object>> resPage =
        bookMapper.getBookList(page, keyWord, bookTypeId);
    Map<String, Object> map =
        Map.of("total", resPage.getTotal(), "list", resPage.getRecords());
    return map;
  }

  private Integer parseInteger(String s) {
    if (s == null)
      return null;
    return Integer.parseInt(s);
  }

  private BigDecimal parseBigDecimal(String s) {
    if (s == null)
      return null;
    return new BigDecimal(s);
  }

  @Override
  public Boolean saveOrUpdateItem(Map<String, String> item) {
    Book book = new Book();
    book.setId(this.parseInteger(item.get("id")));
    book.setTypeId(this.parseInteger(item.get("typeId")));
    book.setName(item.get("name"));
    book.setTime(TimeFormat.parseLocalDateTime(item.get("time")));
    book.setNums(this.parseInteger(item.get("nums")));
    book.setPrice(this.parseBigDecimal(item.get("price")));
    return this.saveOrUpdate(book);
  }

  @Override
  public boolean deleteByIdList(List<Integer> idList) {
    return this.removeByIds(idList);
  }
}
