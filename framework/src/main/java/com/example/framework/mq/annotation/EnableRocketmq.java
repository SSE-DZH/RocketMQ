package com.example.framework.mq.annotation;

import com.example.framework.mq.config.MQConfig;
import com.example.framework.mq.config.MQTransactionListener;
import com.example.framework.mq.handler.impl.DefaultMQHandler;
import com.example.framework.mq.handler.impl.DefaultMQTransactionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({MQConfig.class,
        MQTransactionListener.class,
        DefaultMQHandler.class,
        DefaultMQTransactionHandler.class})
public @interface EnableRocketmq {
}
