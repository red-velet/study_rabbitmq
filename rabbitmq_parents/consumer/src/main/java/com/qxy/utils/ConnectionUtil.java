package com.qxy.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: SayHello
 * @Date: 2023/3/12 19:57
 * @Introduction:
 */
public class ConnectionUtil {

    /**
     * @param host     主机ip地址
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return 长连接
     * @throws IOException      IOException
     * @throws TimeoutException 超时等待未连接异常
     */
    public static Connection getConnection(String host, int port, String username, String password, String virtualHost) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory.newConnection();
    }

    /**
     * 关闭长连接
     *
     * @param connection 连接
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
