package com.lbstspringboot3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

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
     * 书本状态（0-已购买、1-借阅中、2-已归还）
     */
    private Integer status;

    /**
     * 操作时间
     */
    private LocalDateTime time;
}
