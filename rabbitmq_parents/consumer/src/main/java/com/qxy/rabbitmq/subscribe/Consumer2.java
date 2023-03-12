package com.qxy.rabbitmq.subscribe;

import com.qxy.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: SayHello
 * @Date: 2023/3/12 19:43
 * @Introduction: 发布/订阅模式-消费者;使用自定义交换机
 */
public class Consumer2 {
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String FANOUT_QUEUE_NAME_2 = "subscribe_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建长连接
        Connection connection = ConnectionUtil.getConnection("localhost", 5672, "qxy", "qxy", "/");

        //2、创建channel
        Channel channel = connection.createChannel();

        //3、声明队列[放在消费者先启动,发现队列为空]
        // String queue:队列名
        // boolean durable:是否持久化
        // boolean exclusive:是否排它(只能自己使用)
        // boolean autoDelete:是否字段删除(用完删除队列)
        // Map<String, Object> arguments:其它属性参数
        channel.queueDeclare(FANOUT_QUEUE_NAME_2, true, false, false, null);

        //4、声明交换机
        // String exchange,  交换机名称
        // BuiltinExchangeType type, 交换机类型
        // boolean durable,  持久化
        // boolean autoDelete, 自动删除
        // Map<String, Object> arguments 属性
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT, true, false, null);

        //5、队列绑定交换机
        // String queue, 队列名称
        // String exchange, 交换机名称
        // String routingKey 路由键
        channel.queueBind(FANOUT_QUEUE_NAME_2, FANOUT_EXCHANGE, "");

        //5、创建消费者-并设置消息处理
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //消费者标签
                System.out.println("消费者标签为：" + consumerTag);
                //路由key
                System.out.println("路由key为：" + envelope.getRoutingKey());
                //交换机
                System.out.println("交换机为：" + envelope.getExchange());
                //消息id
                System.out.println("消息id为：" + envelope.getDeliveryTag());
                //收到的消息
                System.out.println("接收到的消息为：" + new String(body, "utf-8"));
                System.out.println("----------------------------------------------------");
            }
        };


        //4、监听消息
        // String queue:队列名称
        // boolean autoAck:是否自动确认，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动确认
        // Consumer callback：消息接收到后回调
        channel.basicConsume(FANOUT_QUEUE_NAME_2, true, consumer);
        //5、消费者不需要关闭连接
        //channel.close();
        //connection.close();
    }
}
