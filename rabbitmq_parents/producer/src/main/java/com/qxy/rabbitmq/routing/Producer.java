package com.qxy.rabbitmq.routing;

import com.qxy.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: SayHello
 * @Date: 2023/3/12 19:32
 * @Introduction: Routing模式-生产者;使用自定义交换机
 */
public class Producer {
    public static final String DIRECT_EXCHANGE = "direct_exchange";
    public static final String DIRECT_QUEUE_1 = "direct_queue_1";
    public static final String DIRECT_QUEUE_2 = "direct_queue_2";

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
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT, true, true, null);

        //4、声明队列
        // String queue:队列名
        // boolean durable:是否持久化
        // boolean exclusive:是否排它(只能自己使用)
        // boolean autoDelete:是否字段删除(用完删除队列)
        // Map<String, Object> arguments:其它属性参数
        channel.queueDeclare(DIRECT_QUEUE_1, true, false, false, null);
        channel.queueDeclare(DIRECT_QUEUE_2, true, false, false, null);

        //5、队列绑定交换机
        channel.queueBind(DIRECT_QUEUE_1, DIRECT_EXCHANGE, "error");
        channel.queueBind(DIRECT_QUEUE_2, DIRECT_EXCHANGE, "info");
        channel.queueBind(DIRECT_QUEUE_2, DIRECT_EXCHANGE, "error");
        channel.queueBind(DIRECT_QUEUE_2, DIRECT_EXCHANGE, "warning");


        //6、发送消息
        // String exchange:交换机名
        // String routingKey:路由键
        // BasicProperties props:其它属性
        // byte[] body:待发送的消息
        String msg = "hello rabbitmq!routing error";
        channel.basicPublish(DIRECT_EXCHANGE, "error", null, msg.getBytes());

        String msg1 = "hello rabbitmq!routing info";
        channel.basicPublish(DIRECT_EXCHANGE, "info", null, msg1.getBytes());

        String msg2 = "hello rabbitmq!routing warning";
        channel.basicPublish(DIRECT_EXCHANGE, "warning", null, msg2.getBytes());

        //5、关闭连接
        channel.close();
        connection.close();
    }
}
