package com.qxy.rabbitmq.subscribe;

import com.qxy.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: SayHello
 * @Date: 2023/3/12 19:32
 * @Introduction: 发布/订阅模式-生产者;使用自定义交换机
 */
public class Producer {
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String FANOUT_QUEUE_NAME_1 = "subscribe_queue_1";
    public static final String FANOUT_QUEUE_NAME_2 = "subscribe_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建长连接
        Connection connection = ConnectionUtil.getConnection("localhost", 5672, "qxy", "qxy", "/");

        //2、创建channel
        Channel channel = connection.createChannel();

        //3、声明交换机
        // String exchange:交换机名称
        // BuiltinExchangeType type:交换机类型，fanout、topic、direct、headers
        // boolean durable:是否定义持久化
        // boolean autoDelete:是否在不使用的时候自动删除
        // Map<String, Object> arguments:其它参数
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT, true, true, null);

        //4、声明队列
        // String queue:队列名
        // boolean durable:是否持久化
        // boolean exclusive:是否排它(只能自己使用)
        // boolean autoDelete:是否字段删除(用完删除队列)
        // Map<String, Object> arguments:其它属性参数
        channel.queueDeclare(FANOUT_QUEUE_NAME_1, true, false, false, null);
        channel.queueDeclare(FANOUT_QUEUE_NAME_2, true, false, false, null);

        //5、队列绑定交换机
        channel.queueBind(FANOUT_QUEUE_NAME_1, FANOUT_EXCHANGE, "");
        channel.queueBind(FANOUT_QUEUE_NAME_2, FANOUT_EXCHANGE, "");


        for (int i = 0; i < 10; i++) {
            //6、发送消息
            String msg = "hello rabbitmq!!!" + i + "发布/订阅模式";
            // String exchange:交换机名
            // String routingKey:路由键
            // BasicProperties props:其它属性
            // byte[] body:待发送的消息
            channel.basicPublish(FANOUT_EXCHANGE, "", null, msg.getBytes());
        }

        //5、关闭连接
        channel.close();
        connection.close();
    }
}
