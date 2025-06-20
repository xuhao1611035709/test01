package com.jwvdp.books.handler;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.StreamRequestHandler;
import com.jwvdp.books.BooksApplication;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Handler implements StreamRequestHandler {
    private static volatile BooksApplication application = null;

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        // 首次调用时启动SpringBoot应用
        if (application == null) {
            synchronized (Handler.class) {
                if (application == null) {
                    context.getLogger().info("Starting Spring Boot application...");
                    String[] args = {};
                    SpringApplication.run(BooksApplication.class, args);
                    context.getLogger().info("Spring Boot application started successfully");
                }
            }
        }

        // 请求将由SpringBoot内嵌的Tomcat服务器处理
        // 这里可以添加额外的请求处理逻辑（如果需要）

        // 简单的响应（实际由SpringBoot应用处理）
        String response = "Request forwarded to Spring Boot application";
        outputStream.write(response.getBytes());
    }
}
