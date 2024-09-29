package com.example.order.mqHandler;

import com.alibaba.fastjson.JSONObject;
import com.example.framework.mq.annotation.MQHandlerActualizer;
import com.example.framework.mq.handler.MQTransactionHandler;
import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: rocketmq-example
 * @Package: com.example.order.mqHandler
 * @ClassName: OrderMQTransactionHandler
 * @Author: zlx
 * @Description:
 * @Date: 2021/6/4 12:28
 * @Version: 1.0
 */
@MQHandlerActualizer(topic = "points")
public class OrderMQTransactionHandler implements MQTransactionHandler {
    @Autowired
    private OrderService orderService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        //执行本地事务
        LocalTransactionState state;
        try {
            String body = new String(message.getBody());
            Order order = JSONObject.parseObject(body, Order.class);
            // 执行本地事务
            orderService.save(order);
            state = LocalTransactionState.COMMIT_MESSAGE;
        }catch (Exception e){
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return state;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        //回查本地事务是否已提交
        LocalTransactionState state;
        String body = new String(messageExt.getBody());
        Order tempOrder = JSONObject.parseObject(body, Order.class);
        // 如果数据存在，则事务提交成功
        Order order = orderService.getById(tempOrder.getOrderId());
        if (order != null){
            state = LocalTransactionState.COMMIT_MESSAGE;
        }else{
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return state;
    }
}
