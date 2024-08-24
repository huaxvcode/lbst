package com.lbstspringboot3.controller;

import cn.dev33.satoken.util.SaResult;
import com.lbstspringboot3.service.IBookService;
import com.lbstspringboot3.service.IBookTypeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

  @Autowired IBookService bookService;

  @Autowired IBookTypeService bookTypeService;

  /**
   * 分页查询书籍，一页 pageSize 条数据
   *
   */
  @GetMapping("/list/{page}/{pageSize}/{keyWord}/{bookTypeId}")
  public SaResult
  getBookList(@PathVariable Integer page, @PathVariable Integer pageSize,
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
      return SaResult.ok().setData(bookService.saveOrUpdateItem(item));
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
    return SaResult.ok().setData(bookService.deleteByIdList(idList));
  }
}
