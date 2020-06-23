package com.controller;

import base.api.CommonResult;
import com.config.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/socket")
public class SocketController  {
    //推送数据接口
    @ResponseBody
    @RequestMapping("/push")
    public CommonResult pushToWeb(@RequestParam("message") String message,@RequestParam("id") String id) {
        log.info("message:{},id:{}",message,id);
        try {
            WebSocket socket=new WebSocket();
            socket.sendInfo(message,id);
        } catch (IOException e) {
            log.info(e.getMessage());
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
        return CommonResult.success("发送成功");
    }
}
