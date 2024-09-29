package com.example.points.entity;

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
    @TableName("S_POINTS")
public class Points extends BaseEntity {

    private static final long serialVersionUID=1L;

      /**
     * 积分id
     */
        @TableId(value = "POINTS_ID")
      private Long pointsId;

      /**
     * 订单id
     */
      @TableField("ORDER_ID")
    private Long orderId;

      /**
     * 积分
     */
      @TableField("POINTS")
    private Integer points;


}
