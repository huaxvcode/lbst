package com.lbstspringboot3.service.impl;

import com.lbstspringboot3.entity.BookType;
import com.lbstspringboot3.mapper.BookTypeMapper;
import com.lbstspringboot3.service.IBookTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
@Service
public class BookTypeServiceImpl extends ServiceImpl<BookTypeMapper, BookType> implements IBookTypeService {

}
