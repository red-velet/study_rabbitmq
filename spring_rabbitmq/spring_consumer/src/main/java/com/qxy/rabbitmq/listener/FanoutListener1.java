package com.qxy.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @Author: SayHello
 * @Date: 2023/3/13 9:02
 * @Introduction:
 */
public class FanoutListener1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //获取消息
        System.out.println("发布订阅模式1接收到消息-" + new String(message.getBody()));
    }
}
