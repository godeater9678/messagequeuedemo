package com.bithumb.messagequeue.messagequeuedemo.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseResult {
    private HttpStatus status;
    private String message;
}
