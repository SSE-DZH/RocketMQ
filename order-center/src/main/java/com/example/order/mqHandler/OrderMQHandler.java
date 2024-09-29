package com.example.order.mqHandler;

import com.example.framework.mq.annotation.MQHandlerActualizer;
import com.example.framework.mq.handler.MQHandler;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @ProjectName: rocketmq-example
 * @Package: com.example.order.mqHandler
 * @ClassName: OrderMQHandler
 * @Author: zlx
 * @Description:
 * @Date: 2021/6/4 14:32
 * @Version: 1.0
 */
@MQHandlerActualizer(topic = "order")
public class OrderMQHandler implements MQHandler {
    @Override
    public ConsumeConcurrentlyStatus handle(String tag, MessageExt messageExt) {
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
