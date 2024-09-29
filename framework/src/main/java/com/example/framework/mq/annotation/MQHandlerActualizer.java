package com.example.framework.mq.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 消息处理类标注（根据topic和tag区分不同消息处理类）
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
public @interface MQHandlerActualizer {
    /**
     * 消息主题
     */
    String topic() default "";

    /**
     * 消息标签,如果是该主题下所有的标签，使用“*”
     */
    String[] tags() default "*";

    /**
     * 消息处理类备注
     **/
    String remark() default "";
}
