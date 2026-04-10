package com.bird_forum;

import com.bird_forum.util.ModelUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BirdForumApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("\"今天天气如何？\"运行结果: " + (ModelUtils.chat("今天天气如何？", Boolean.class) ? "好!" : "不好!"));
    }

}
