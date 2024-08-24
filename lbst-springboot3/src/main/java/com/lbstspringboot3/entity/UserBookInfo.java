package com.lbstspringboot3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("user_book_info")
public class UserBookInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户 id（外键）
     */
    private Integer uid;

    /**
     * 书籍 id（外键）
     */
    private Integer bookId;

    /**
     * 操作时间
     */
    private LocalDateTime time;

    /**
     * 操作类型（0-已购买、1-借阅中、2-已归还）
     */
    private Integer status;

    /**
     * 书籍价格
     */
    private BigDecimal price;

    /**
     * 归还时间
     */
    private LocalDateTime returnTime;

    /**
     * 截止时间
     */
    private LocalDateTime deadline;
}
