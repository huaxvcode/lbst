package com.lbstspringboot3.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbstspringboot3.entity.BookLog;
import com.lbstspringboot3.mapper.BookLogMapper;
import com.lbstspringboot3.service.IBookLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
@Service
public class BookLogServiceImpl extends ServiceImpl<BookLogMapper, BookLog> implements IBookLogService {

	@Autowired
	private BookLogMapper bookLogMapper;

	@Override
	public List<Map<String, Object>> getBookLogList(Integer pageNum, Integer pageSize) {
		IPage<Map<String, Object>> page = new Page<>(pageNum, pageSize);
		page = bookLogMapper.getBookLogList(page);
		if (page.getRecords() == null) return new ArrayList<>();
		return page.getRecords();
	}

	@Override
	public void saveBookLog(Integer uid, Integer bookId, String content) {
		if (uid == null || bookId == null) return;
		BookLog bookLog = new BookLog();
		bookLog.setBookId(bookId);
		bookLog.setContent(content);
		bookLog.setUid(uid);
		bookLog.setTime(LocalDateTime.now());
		this.save(bookLog);
	}
}
