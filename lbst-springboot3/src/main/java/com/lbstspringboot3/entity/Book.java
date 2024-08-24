package com.lbstspringboot3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 书籍类型（外键）
     */
    private Integer typeId;

    /**
     * 书名
     */
    private String name;

    /**
     * 发布时间
     */
    private LocalDateTime time;

    /**
     * 数量
     */
    private Integer nums;

    /**
     * 价格（NULL-非卖品）
     */
    private BigDecimal price;
}
