package com.lbstspringboot3.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.lbstspringboot3.service.IBookLogService;
import com.lbstspringboot3.service.IBookService;
import com.lbstspringboot3.service.IBookTypeService;
import com.lbstspringboot3.service.IUserBookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	IBookService bookService;

	@Autowired
	IBookTypeService bookTypeService;

	@Autowired
	IUserBookInfoService userBookInfoService;

	@Autowired
	IBookLogService bookLogService;

	/**
	 * 分页查询书籍，一页 pageSize 条数据
	 */
	@GetMapping("/list/{page}/{pageSize}/{keyWord}/{bookTypeId}")
	public SaResult getBookList(@PathVariable Integer page, @PathVariable Integer pageSize,
	                            @PathVariable String keyWord, @PathVariable Integer bookTypeId) {
		if (keyWord != null) {
			if (keyWord.equals("null") || keyWord.isEmpty() || keyWord.isBlank()) {
				keyWord = null;
			}
		}

		return SaResult.ok().setData(
				bookService.getBookList(page, pageSize, keyWord, bookTypeId));
	}

	@PostMapping("/save")
	public SaResult saveItem(@RequestBody Map<String, String> item) {
		try {
			System.out.println(item);
			Integer id = null;
			var res = bookService.saveOrUpdateItem(item);
			if (item.get("id") != null) id = Integer.parseInt(item.get("id"));
			else id = bookService.getByBookName(item.get("name"));
			bookLogService.saveBookLog(StpUtil.getLoginIdAsInt(), id, "保存或更改书籍信息");
			return SaResult.ok().setData(res);
		} catch (Exception e) {
			System.out.println(e);
			return SaResult.error();
		}
	}

	@GetMapping("/type")
	public SaResult getBookType() {
		return SaResult.ok().setData(bookTypeService.list());
	}

	@PostMapping("/deleteByIdList")
	public SaResult deleteByIdList(@RequestBody List<Integer> idList) {
		if (idList != null && idList.size() > 0) {
			idList.forEach(id -> {
				bookLogService.saveBookLog(StpUtil.getLoginIdAsInt(), id, "删除书籍");
			});
		}
		return SaResult.ok().setData(Boolean.valueOf(bookService.deleteByIdList(idList)));
	}

	@GetMapping("/borrow/{bookId}")
	public SaResult borrow(@PathVariable Integer bookId) {
		if (bookId == null) {
			return SaResult.error();
		}
		if (bookService.borrow(bookId)) {
			bookLogService.saveBookLog(StpUtil.getLoginIdAsInt(), bookId, "借书");
			Integer userId = Integer.valueOf(StpUtil.getLoginIdAsInt());
			if (userBookInfoService.borrow(userId, bookId)) {
				return SaResult.ok().setData(Boolean.TRUE);
			} else return SaResult.error();
		} else {
			return SaResult.error();
		}
	}

	@GetMapping("/getBorrowList")
	public SaResult getBorrowList() {
		Integer uid = Integer.valueOf(StpUtil.getLoginIdAsInt());
		List<Map<String, Object>> res = userBookInfoService.getBorrowListByUid(uid);
		if (res == null) return SaResult.error();
		return SaResult.ok().setData(res);
	}

	@GetMapping("/returnBook/{id}")
	public SaResult returnBook(@PathVariable Integer id) {
		if (id == null) {
			return SaResult.error();
		}
		bookLogService.saveBookLog(StpUtil.getLoginIdAsInt(), id, "还书");
		return SaResult.ok().setData(userBookInfoService.returnBook(id));
	}
}
