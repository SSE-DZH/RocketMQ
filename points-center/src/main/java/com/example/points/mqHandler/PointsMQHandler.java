package com.example.points.mqHandler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.framework.mq.annotation.MQHandlerActualizer;
import com.example.framework.mq.handler.MQHandler;
import com.example.framework.utils.SonwflakeUtils;
import com.example.points.entity.Points;
import com.example.points.service.PointsService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @ProjectName: rocketmq-example
 * @Package: com.example.points.mqHandler
 * @ClassName: PointsMQHandler
 * @Author: zlx
 * @Description:
 * @Date: 2021/6/4 13:03
 * @Version: 1.0
 */
@MQHandlerActualizer(topic = "points")
public class PointsMQHandler implements MQHandler {
    @Autowired
    private PointsService pointsService;

    @Override
    public ConsumeConcurrentlyStatus handle(String tag, MessageExt messageExt) {
        //消息监听
        String messageStr = new String(messageExt.getBody());
        Map orderMap = (Map) JSON.parse(messageStr);
        Points points = new Points();
        Long orderId = (Long) orderMap.get("orderId");
        System.out.println("消息tag为：" + tag);
        System.out.println("消息监听："  + "为订单" + orderId + "添加积分");
        //查询该订单是否已经生成对应积分（rocketMQ可能会重复发送消息，需实现幂等）
        QueryWrapper<Points> pointsQueryWrapper = new QueryWrapper<>();
        pointsQueryWrapper.lambda().eq(Points::getOrderId,orderId);
        Points tempPoints = pointsService.getOne(pointsQueryWrapper);
        if (tempPoints != null) {
            //该订单已经生成积分
            System.out.println(orderId + "已经生成积分");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        points.setPointsId(SonwflakeUtils.get().id());
        points.setOrderId(orderId);
        Integer orderAmout = (Integer) orderMap.get("orderAmout");
        points.setPoints(orderAmout * 10);
        pointsService.save(points);
        return  ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
