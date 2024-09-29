package com.example.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.framework.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zlx
 * @since 2021-06-04
 */
@Data
  @EqualsAndHashCode(callSuper = true)
    @TableName("S_ORDER")
public class Order extends BaseEntity {

    private static final long serialVersionUID=1L;

      /**
     * 订单id
     */
        @TableId(value = "ORDER_ID")
      private Long orderId;

      /**
     * 用户id
     */
      @TableField("USER_ID")
    private Long userId;

      /**
     * 订单金额
     */
      @TableField("ORDER_AMOUT")
    private Long orderAmout;

    /**
     * 发送消息类型
     */
    @TableField(exist = false)
    private Integer messageType;
}
