package com.yushu.engine.controller;


import com.yushu.engine.config.NettyServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 通讯协议日志 前端控制器
 * </p>
 *
 * @author yushu
 * @since 2023-03-23
 */
@RestController
@RequestMapping("/communication-protocol-log")
public class CommunicationProtocolLogController {
    @Autowired
    private NettyServerConfig handler;

    @GetMapping()
    private String echo(String s){
        handler.echo(s);
        return "success";
    }

}
