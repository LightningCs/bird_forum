package com.bird_forum.context;

public class ThreadContext {
    private static final ThreadLocal<Long> local = new ThreadLocal<>();

    // 存储
    public static void set(Long id) {
        local.set(id);
    }

    // 获取
    public static Long get() {
        return local.get();
    }

    // 删除
    public static void clear() {
        local.remove();
    }
}
