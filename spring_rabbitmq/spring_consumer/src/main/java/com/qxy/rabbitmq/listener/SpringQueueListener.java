package com.qxy.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @Author: SayHello
 * @Date: 2023/3/13 8:52
 * @Introduction: 监听器, 监听简单模式队列
 */
public class SpringQueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //获取消息
        System.out.println("简单工作模式接收到消息-" + new String(message.getBody()));
    }

    
}
