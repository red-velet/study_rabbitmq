package com.qxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: SayHello
 * @Date: 2023/3/13 8:12
 * @Introduction: 测试spring整合rabbitmq-消费者演示
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ConsumerTest {
    /**
     * spring整合rabbitmq-consumer测试
     */
    @Test
    public void consumerTest() {
        while (true) {

        }
    }
}
