package com.lbstspringboot3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbstspringboot3.entity.UserBookInfo;

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
public interface IUserBookInfoService extends IService<UserBookInfo> {

	boolean borrow(Integer userId, Integer bookId);

	List<Map<String, Object>> getBorrowListByUid(Integer uid);

	boolean returnBook(Integer id);
}
