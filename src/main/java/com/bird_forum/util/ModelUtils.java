package com.bird_forum.util;

import com.bird_forum.config.ChatClientConfig;

public class ModelUtils {

    /**
     * 模型调用
     *
     * @param content 问题
     * @param classes 类
     * @param <T>     泛型
     * @return
     */
    public static <T> T chat(String content, Class<T> classes) {
        return ChatClientConfig.chatClient
                .prompt()
                .user(content) // 用户提问
                .call() // 调用模型
                .entity(classes);
    }
}
