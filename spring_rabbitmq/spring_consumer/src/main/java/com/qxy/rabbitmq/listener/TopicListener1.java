package com.qxy.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @Author: SayHello
 * @Date: 2023/3/13 9:06
 * @Introduction:
 */
public class TopicListener1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("topics工作模式1接收到消息-" + new String(message.getBody()));
    }
}
