package com.example.order.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.example.framework.utils.SonwflakeUtils;
import com.example.order.entity.Order;
import com.example.order.mapper.OrderMapper;
import com.example.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zlx
 * @since 2021-06-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Qualifier("default")
    @Autowired
    private DefaultMQProducer producer;

    @Autowired
    private TransactionMQProducer transactionMQProducer;

    /**
     * 添加订单（发送消息积分模块同步添加积分）
     * zlx
     * 12:09 2021/6/4
     * @param order 订单信息
     * @return org.apache.rocketmq.client.producer.TransactionSendResult
     **/
    @Override
    public Order addOder(Order order) {
        order.setOrderId(SonwflakeUtils.get().id());
        if (order.getMessageType() == 1) {
            //普通消息
            this.save(order);
            Message message = new Message("points","default", JSON.toJSONString(order).getBytes());
            try {
                SendResult sendResult = producer.send(message);//同步消息
                System.out.println("发送状态：" + sendResult.getSendStatus() +
                        ",消息ID：" + sendResult.getMsgId() +
                        ",队列:" + sendResult.getMessageQueue().getQueueId());
//                producer.sendOneway(message);//单向消息
//                producer.send(message, new SendCallback() {//异步消息
//                    @Override
//                    public void onSuccess(SendResult sendResult) {
//
//                    }
//
//                    @Override
//                    public void onException(Throwable throwable) {
//
//                    }
//                });
            } catch (RemotingException | MQBrokerException | InterruptedException | MQClientException e) {
                e.printStackTrace();
            }
        } else {
            //事务消息
            Message message = new Message("points","transaction", JSON.toJSONString(order).getBytes());
            try {
                transactionMQProducer.sendMessageInTransaction(message, null);
            } catch (MQClientException e) {
                e.printStackTrace();
            }
        }
        return order;
    }
}
