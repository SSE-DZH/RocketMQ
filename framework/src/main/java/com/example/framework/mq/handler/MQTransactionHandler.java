package com.example.framework.mq.handler;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

public interface MQTransactionHandler {
    LocalTransactionState executeLocalTransaction(Message message, Object o);
    LocalTransactionState checkLocalTransaction(MessageExt messageExt);
}
