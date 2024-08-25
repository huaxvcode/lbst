package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.BookLog;

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
public interface IBookLogService extends IService<BookLog> {

	List<Map<String, Object>> getBookLogList(Integer pageNum, Integer pageSize);

	void saveBookLog(Integer uid, Integer bookId, String content);
}
