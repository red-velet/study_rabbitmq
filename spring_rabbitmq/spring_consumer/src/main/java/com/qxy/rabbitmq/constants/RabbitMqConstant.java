package com.qxy.rabbitmq.constants;

/**
 * @Author: SayHello
 * @Date: 2023/3/13 8:33
 * @Introduction:
 */
public class RabbitMqConstant {
    /**
     * 简单模式
     */
    public static final String SPRING_QUEUE = "spring_queue";

    /**
     * 发布/订阅模式
     */
    public static final String SPRING_FANOUT_EXCHANGE = "spring_fanout_exchange";
    public static final String SPRING_FANOUT_QUEUE_1 = "spring_fanout_queue_1";
    public static final String SPRING_FANOUT_QUEUE_2 = "spring_fanout_queue_2";

    /**
     * routing模式
     */
    public static final String SPRING_DIRECT_EXCHANGE = "spring_direct_exchange";
    public static final String SPRING_DIRECT_QUEUE_1 = "spring_direct_queue_1";
    public static final String SPRING_DIRECT_QUEUE_2 = "spring_direct_queue_2";

    /**
     * topics模式
     */
    public static final String SPRING_TOPIC_EXCHANGE = "spring_topic_exchange";
    public static final String SPRING_TOPIC_QUEUE_1 = "spring_topic_queue_1";
    public static final String SPRING_TOPIC_QUEUE_2 = "spring_topic_queue_2";

}
