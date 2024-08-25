package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.OpLog;

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
public interface IOpLogService extends IService<OpLog> {

	List<Map<String, Object>> getOpLogList(Integer pageNum, Integer pageSize);

	void saveOpLog(Integer uid, String ip, String content);
}
