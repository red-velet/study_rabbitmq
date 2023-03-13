package com.qxy.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @Author: SayHello
 * @Date: 2023/3/13 9:06
 * @Introduction:
 */
public class DirectListener2 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //获取消息
        System.out.println("routing工作模式2接收到消息-" + new String(message.getBody()));
    }
}
