package com.lbstspringboot3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbstspringboot3.entity.UserBookInfo;
import com.lbstspringboot3.mapper.UserBookInfoMapper;
import com.lbstspringboot3.service.IUserBookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class UserBookInfoServiceImpl extends ServiceImpl<UserBookInfoMapper, UserBookInfo> implements IUserBookInfoService {

	@Autowired
	private UserBookInfoMapper userBookInfoMapper;

	@Override
	public boolean borrow(Integer userId, Integer bookId) {
		UserBookInfo userBookInfo = new UserBookInfo();
		userBookInfo.setUid(userId);
		userBookInfo.setBookId(bookId);
		userBookInfo.setStatus(1);
		userBookInfo.setTime(LocalDateTime.now());
		userBookInfo.setDeadline(LocalDateTime.now().plusMonths(1));
		return this.save(userBookInfo);
	}

	@Override
	public List<Map<String, Object>> getBorrowListByUid(Integer uid) {
		return userBookInfoMapper.getBorrowListByUid(uid);
	}

	@Override
	public boolean returnBook(Integer id) {
		UserBookInfo userBookInfo = this.getById(id);
		if (userBookInfo == null) return false;
		if (userBookInfo.getStatus() == 2) {
			return false;
		}
		userBookInfo.setStatus(2);
		userBookInfo.setReturnTime(LocalDateTime.now());
		return this.saveOrUpdate(userBookInfo);
	}
}
