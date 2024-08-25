package com.lbstspringboot3.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbstspringboot3.entity.OpLog;
import com.lbstspringboot3.mapper.OpLogMapper;
import com.lbstspringboot3.service.IOpLogService;
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
public class OpLogServiceImpl extends ServiceImpl<OpLogMapper, OpLog> implements IOpLogService {

	@Autowired
	private OpLogMapper opLogMapper;

	@Override
	public List<Map<String, Object>> getOpLogList(Integer pageNum, Integer pageSize) {
		IPage<Map<String, Object>> page = new Page<>(pageNum, pageSize);
		page = opLogMapper.getOpLogList(page);
		if (page.getRecords() == null) {
			return new ArrayList<>();
		}
		return page.getRecords();
	}

	@Override
	public void saveOpLog(Integer uid, String ip, String content) {
		if (uid == null) return;
		OpLog opLog = new OpLog();
		opLog.setUid(uid);
		opLog.setIp(ip);
		opLog.setContent(content);
		opLog.setTime(LocalDateTime.now());
		this.save(opLog);
	}
}
