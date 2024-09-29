package com.example.order.service;

import com.example.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.rocketmq.client.producer.TransactionSendResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zlx
 * @since 2021-06-04
 */
public interface OrderService extends IService<Order> {
    Order addOder(Order order);
}
