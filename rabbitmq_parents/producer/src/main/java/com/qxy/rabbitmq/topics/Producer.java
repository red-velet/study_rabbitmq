package com.qxy.rabbitmq.topics;

import com.qxy.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: SayHello
 * @Date: 2023/3/12 19:32
 * @Introduction: Topics通配符模式-生产者;使用自定义交换机
 */
public class Producer {
    public static final String TOPIC_EXCHANGE = "topic_exchange";
    public static final String TOPIC_QUEUE_1 = "topic_queue_1";
    public static final String TOPIC_QUEUE_2 = "topic_queue_2";

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
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC, true, true, null);

        //4、声明队列
        // String queue:队列名
        // boolean durable:是否持久化
        // boolean exclusive:是否排它(只能自己使用)
        // boolean autoDelete:是否字段删除(用完删除队列)
        // Map<String, Object> arguments:其它属性参数
        channel.queueDeclare(TOPIC_QUEUE_1, true, false, false, null);
        channel.queueDeclare(TOPIC_QUEUE_2, true, false, false, null);

        //5、队列绑定交换机
        //我是太原校区校长的队列
        channel.queueBind(TOPIC_QUEUE_1, TOPIC_EXCHANGE, "ydlclass.taiyuan.*.*");
        //我是总部财务主管的队列
        channel.queueBind(TOPIC_QUEUE_2, TOPIC_EXCHANGE, "ydlclass.*.caiwubu.*");


        //6、发送消息
        // String exchange:交换机名
        // String routingKey:路由键
        // BasicProperties props:其它属性
        // byte[] body:待发送的消息
        String msg1 = "hello rabbitmq!topic  本月工资大家涨两千！";
        channel.basicPublish(TOPIC_EXCHANGE, "ydlclass.taiyuan.caiwubu.info", null, msg1.getBytes());

        String msg2 = "hello rabbitmq!topic 李老师携款潜逃！";
        channel.basicPublish(TOPIC_EXCHANGE, "ydlclass.taiyuan.renshi.error", null, msg2.getBytes());

        String msg3 = "hello rabbitmq!topic  因为李老师逃了，全国所有校区降薪两千。不行就毕业！";
        channel.basicPublish(TOPIC_EXCHANGE, "ydlclass.beijing.caiwubu.error", null, msg3.getBytes());

        //5、关闭连接
        channel.close();
        connection.close();
    }
}
