package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.UserBookInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
public interface IUserBookInfoService extends IService<UserBookInfo> {
	
	boolean borrow(Integer userId, Integer bookId);
}
