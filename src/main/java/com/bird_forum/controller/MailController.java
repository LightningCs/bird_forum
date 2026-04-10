package com.bird_forum.controller;

import com.bird_forum.domain.ResponseData;
import com.bird_forum.util.MailUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "邮箱模块")
public class MailController {

    // 存放验证码
    public static final Map<String, String> codeMap = new HashMap<>();

    @GetMapping
    public ResponseData sendMail(String account) throws MessagingException {
        log.info("发送邮件");

        String code = String.valueOf(Math.round(Math.random() * 10000));

        codeMap.put(account, code);
        MailUtils.sendSimpleText(account, "验证码", code);

        return ResponseData.success();
    }
}
