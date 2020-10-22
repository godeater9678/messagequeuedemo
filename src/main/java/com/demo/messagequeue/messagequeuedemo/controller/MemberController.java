package com.bithumb.messagequeue.messagequeuedemo.controller;

import com.bithumb.messagequeue.messagequeuedemo.service.ProducerService;
import com.bithumb.messagequeue.messagequeuedemo.vo.LoginRequest;
import com.bithumb.messagequeue.messagequeuedemo.vo.MessageQueueLogin;
import com.bithumb.messagequeue.messagequeuedemo.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RequestMapping("/v1/member")
@RestController
@Api(tags="회원")
public class MemberController {
    @Autowired
    private ProducerService<MessageQueueLogin> producerService;
    static final String topicLogin = "testTopic-1";
    static final Random rnd = new Random(10000);
    @Value("${kafka.topic.loginPushTopicName}")
    String loginPushTopicName;

    @ApiOperation(value="로그인과 함께 푸시 Queue를 날립니다")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult doLogin(@RequestBody LoginRequest loginRequest){
        var messageQueueLogin = new MessageQueueLogin();

        messageQueueLogin.setTopicName(loginPushTopicName);
        messageQueueLogin.setPhoneNumber("010"+rnd.nextInt()+rnd.nextInt());
        messageQueueLogin.setMessage(loginRequest.getId()+" logged in!!");
        producerService.send(topicLogin, messageQueueLogin);

        var response = new ResponseResult();
        response.setStatus(HttpStatus.OK);
        response.setMessage("success");

        return response;
    }
}
