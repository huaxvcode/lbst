package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.Book;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
public interface IBookService extends IService<Book> {

	Map<String, Object> getBookList(Integer page, Integer pageSize,
	                                String keyWord, Integer bookTypeId);

	Boolean saveOrUpdateItem(Map<String, String> item);

	boolean deleteByIdList(List<Integer> idList);

	boolean borrow(Integer bookId);
}
