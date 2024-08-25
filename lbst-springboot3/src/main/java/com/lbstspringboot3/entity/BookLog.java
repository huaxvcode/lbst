package com.lbstspringboot3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 林日清
 * @since 2024-08-19
 */
@Getter
@Setter
@TableName("book_log")
public class BookLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键 id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	/**
	 * 书籍 id（外键）
	 */
	private Integer bookId;

	/**
	 * 日志内容
	 */
	private String content;

	/**
	 * 操作时间
	 */
	private LocalDateTime time;

	/**
	 * 用户uid
	 */
	private Integer uid;
}
