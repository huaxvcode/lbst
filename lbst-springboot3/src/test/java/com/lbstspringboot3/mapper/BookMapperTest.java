package com.lbstspringboot3.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookMapperTest {

  @Autowired BookMapper bookMapper;

  @Test
  public void getBookList() {
    IPage<Map<String, Object>> page = new Page<>(1, 10);
    var res = bookMapper.getBookList(page, null);
    System.out.println(res.getTotal());
    System.out.println(res.getRecords());
  }
}
