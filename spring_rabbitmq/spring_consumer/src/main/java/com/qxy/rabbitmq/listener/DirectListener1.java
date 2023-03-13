package com.qxy.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @Author: SayHello
 * @Date: 2023/3/13 9:05
 * @Introduction:
 */
public class DirectListener1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //获取消息
        System.out.println("routing工作模式1接收到消息-" + new String(message.getBody()));
    }
}
