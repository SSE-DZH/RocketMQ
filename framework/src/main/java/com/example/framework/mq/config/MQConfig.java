package com.example.framework.mq.config;

import cn.hutool.core.thread.ThreadUtil;
import com.example.framework.mq.handler.MQHandler;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * MQ配置
 */
@Configuration
public class MQConfig {
    @Value("${spring.application.name:application}")
    private String groupName;//集群名称，这边以应用名称作为集群名称

    /***************************消息生产者***************************/
    @Autowired
    private Map<String, MQHandler> mqHandlerMap;
    //TODO 消息消费者配置信息
    @Value("${rocketmq.consumer.namesrvAddr:127.0.0.1:9876}")
    private String cNamesrvAddr;                                //TODO 消费者nameservice地址
    @Value("${rocketmq.consumer.consumeThreadMin:20}")
    private int consumeThreadMin;                               //TODO 最小线程数
    @Value("${rocketmq.consumer.consumeThreadMax:64}")
    private int consumeThreadMax;                               //TODO 最大线程数
    @Value("${rocketmq.consumer.topics:test~*}")
    private String topics;                                      //TODO 消费者监听主题，多个主题以分号隔开（topic~tag;topic~tag）
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize:1}")
    private int consumeMessageBatchMaxSize;                     //TODO 一次消费消息的条数，默认为1条


    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(cNamesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.registerMessageListener(getMessageListenerConcurrently());
        //TODO 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费,如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //TODO 设置消费模型，集群还是广播，默认为集群
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        //TODO 设置一次消费消息的条数，默认为1条
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        try {
            //TODO 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
            String[] topicTagsArr = topics.split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("~");
                consumer.subscribe(topicTag[0],topicTag[1]);
            }
            consumer.start();
        }catch (Exception e){
            throw new Exception(e);
        }
        return consumer;
    }
    //TODO 并发消息侦听器(如果对顺序消费有需求则使用MessageListenerOrderly 有序消息侦听器)
    @Bean
    public MessageListenerConcurrently getMessageListenerConcurrently() {
        return new MQListenerConcurrently(mqHandlerMap);
    }

    /***************************消息生产者***************************/
    @Autowired
    private MQTransactionListener mqTransactionListener;        //TODO 事务消息监听器
    //TODO 消息生产者配置信息
    @Value("${rocketmq.producer.namesrvAddr:127.0.0.1:9876}")
    private String pNamesrvAddr;                                //TODO 生产者nameservice地址
    @Value("${rocketmq.producer.maxMessageSize:4096}")
    private Integer maxMessageSize ;                            //TODO 消息最大大小，默认4M
    @Value("${rocketmq.producer.sendMsgTimeout:30000}")
    private Integer sendMsgTimeout;                             //TODO 消息发送超时时间，默认3秒
    @Value("${rocketmq.producer.retryTimesWhenSendFailed:2}")
    private Integer retryTimesWhenSendFailed;                   //TODO 消息发送失败重试次数，默认2次
    private static ExecutorService executor = ThreadUtil.newExecutor(32);//TODO 执行任务的线程池

    //普通消息生产者
    @Bean("default")
    public DefaultMQProducer getDefaultMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(this.groupName);
        producer.setNamesrvAddr(this.pNamesrvAddr);
        producer.setMaxMessageSize(this.maxMessageSize);
        producer.setSendMsgTimeout(this.sendMsgTimeout);
        producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
        try {
            producer.start();
        } catch (MQClientException e) {
            System.out.println(e.getErrorMessage());
        }
        return producer;
    }

    //事务消息生产者（rocketmq支持柔性事务）
    @Bean("transaction")
    public TransactionMQProducer getTransactionMQProducer() {
        //初始化事务消息基本与普通消息生产者一致
        TransactionMQProducer producer = new TransactionMQProducer("transaction_" + this.groupName);
        producer.setNamesrvAddr(this.pNamesrvAddr);
        producer.setMaxMessageSize(this.maxMessageSize);
        producer.setSendMsgTimeout(this.sendMsgTimeout);
        producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);

        //添加事务消息处理线程池
        producer.setExecutorService(executor);
        //添加事务消息监听
        producer.setTransactionListener(mqTransactionListener);
        try {
            producer.start();
        } catch (MQClientException e) {
            System.out.println(e.getErrorMessage());
        }
        return producer;
    }
}
