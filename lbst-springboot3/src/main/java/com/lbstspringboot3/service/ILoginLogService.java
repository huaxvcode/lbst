package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.LoginLog;

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
public interface ILoginLogService extends IService<LoginLog> {

	List<Map<String, Object>> getLoginLogList(Integer pageNum, Integer pageSize);

	void saveLoginLog(Integer uid, String ip, String content);
}
