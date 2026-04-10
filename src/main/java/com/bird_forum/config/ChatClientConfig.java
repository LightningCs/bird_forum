package com.bird_forum.config;

import lombok.Getter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class ChatClientConfig {
    // 提示词文件名
    @Value("${spring.ai.openai.chat.prompt}")
    private String promptFileName;

    @Getter
    public static ChatClient chatClient;

    @Bean
    public ChatClient chatClient(Builder builder) {
        try {
            // 读取提示词文件
            String prompt = Files.readString(Paths.get(promptFileName));
            ChatClient chatClient = builder
                    .defaultSystem(prompt)
                    .build();

            ChatClientConfig.chatClient = chatClient;

            return chatClient;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
