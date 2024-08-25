package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.WrongLog;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
public interface IWrongLogService extends IService<WrongLog> {

	List<WrongLog> getErrorLogList(Integer pageNum, Integer pageSize);
}
