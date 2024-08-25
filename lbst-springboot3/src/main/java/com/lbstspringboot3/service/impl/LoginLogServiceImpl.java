package com.lbstspringboot3.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbstspringboot3.entity.LoginLog;
import com.lbstspringboot3.mapper.LoginLogMapper;
import com.lbstspringboot3.service.ILoginLogService;
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
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

	@Autowired
	private LoginLogMapper loginLogMapper;

	@Override
	public List<Map<String, Object>> getLoginLogList(Integer pageNum, Integer pageSize) {
		IPage<Map<String, Object>> page = new Page<>(pageNum, pageSize);
		page = loginLogMapper.getLoginLogList(page);
		if (page.getRecords() == null) return new ArrayList<>();
		return page.getRecords();
	}

	@Override
	public void saveLoginLog(Integer uid, String ip, String content) {
		LoginLog loginLog = new LoginLog();
		loginLog.setUid(uid);
		loginLog.setIp(ip);
		loginLog.setContent(content);
		loginLog.setTime(LocalDateTime.now());
		this.save(loginLog);
	}
}
