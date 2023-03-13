package com.qxy;

import com.qxy.rabbitmq.constants.RabbitMqConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: SayHello
 * @Date: 2023/3/13 8:12
 * @Introduction: 测试spring整合rabbitmq-生产者演示
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ProducerTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * spring整合rabbitmq-简单模式测试
     */
    @Test
    public void simpleTest() {
        String msg = "hello simpleTest!!!";
        //  String exchange:交换机名称
        // String routingKey:路由键,此处是队列名
        // Object object: Obj类型的消息对象,会自动转化为byte数组
        rabbitTemplate.convertAndSend("", RabbitMqConstant.SPRING_QUEUE, msg);
    }

    /**
     * spring整合rabbitmq-发布/订阅模式测试
     */
    @Test
    public void publishSubscribeTest() {
        String msg = "hello publishSubscribeTest!!!";
        // String exchange:交换机名称
        // String routingKey:路由键
        // Object object: Obj类型的消息对象,会自动转化为byte数组
        rabbitTemplate.convertAndSend(RabbitMqConstant.SPRING_FANOUT_EXCHANGE, "", msg);
    }

    /**
     * spring整合rabbitmq-routing模式测试
     */
    @Test
    public void routingTest() {
        String msg = "hello publishSubscribeTest!!!";
        // String exchange:交换机名称
        // String routingKey:路由键
        // Object object: Obj类型的消息对象,会自动转化为byte数组
        rabbitTemplate.convertAndSend(RabbitMqConstant.SPRING_DIRECT_EXCHANGE, "info", msg);
        rabbitTemplate.convertAndSend(RabbitMqConstant.SPRING_DIRECT_EXCHANGE, "error", msg);
        rabbitTemplate.convertAndSend(RabbitMqConstant.SPRING_DIRECT_EXCHANGE, "warning", msg);
        rabbitTemplate.convertAndSend(RabbitMqConstant.SPRING_DIRECT_EXCHANGE, "info", msg);
        rabbitTemplate.convertAndSend(RabbitMqConstant.SPRING_DIRECT_EXCHANGE, "info", msg);
    }

    /**
     * spring整合rabbitmq-topics模式测试
     */
    @Test
    public void topicsTest() {
        String msg1 = "hello rabbitmq!topic  本月工资大家涨两千！";
        rabbitTemplate.convertAndSend("spring_topic_exchange", "ydlclass.taiyuan.caiwubu.info", msg1);

        String msg2 = "hello rabbitmq!topic 李老师携款潜逃！";
        rabbitTemplate.convertAndSend("spring_topic_exchange", "ydlclass.taiyuan.renshi.error", msg2);

        String msg3 = "hello rabbitmq!topic  因为李老师逃了，全国所有校区降薪两千。不行就毕业！";
        rabbitTemplate.convertAndSend("spring_topic_exchange", "ydlclass.beijing.caiwubu.error", msg3);

    }
}
