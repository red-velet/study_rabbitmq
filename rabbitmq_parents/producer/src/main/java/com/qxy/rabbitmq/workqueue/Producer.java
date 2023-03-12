package com.qxy.rabbitmq.workqueue;

import com.qxy.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: SayHello
 * @Date: 2023/3/12 19:32
 * @Introduction: 工作队列模式-生产者
 */
public class Producer {
    public static final String QUEUE_NAME = "work_queue_mode";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建长连接
        Connection connection = ConnectionUtil.getConnection("localhost", 5672, "qxy", "qxy", "/");

        //2、创建channel
        Channel channel = connection.createChannel();

        //3、声明队列
        // String queue:队列名
        // boolean durable:是否持久化
        // boolean exclusive:是否排它(只能自己使用)
        // boolean autoDelete:是否字段删除(用完删除队列)
        // Map<String, Object> arguments:其它属性参数
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        for (int i = 0; i < 10; i++) {
            //4、发送消息
            String msg = "hello rabbitmq!!!" + i + "工作队列模式";
            // String exchange:交换机名
            // String routingKey:路由键
            // BasicProperties props:其它属性
            // byte[] body:待发送的消息
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }

        //5、关闭连接
        channel.close();
        connection.close();
    }
}
