package com.qxy.rabbitmq.topics;

import com.qxy.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: SayHello
 * @Date: 2023/3/12 19:43
 * @Introduction: Topics通配符模式-消费者;使用自定义交换机
 */
public class Consumer2 {
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

        //5、创建消费者-并设置消息处理
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //收到的消息
                System.out.println("接收到的消息为：" + new String(body, "utf-8"));
                System.out.println("----------------------------------------------------");
            }
        };

        //4、监听消息
        // String queue:队列名称
        // boolean autoAck:是否自动确认，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动确认
        // Consumer callback：消息接收到后回调
        channel.basicConsume(TOPIC_QUEUE_2, true, consumer);
        //5、消费者不需要关闭连接
        //channel.close();
        //connection.close();
    }
}
