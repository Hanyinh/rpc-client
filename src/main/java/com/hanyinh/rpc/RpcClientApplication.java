package com.hanyinh.rpc;

import com.alibaba.fastjson.JSON;
import com.hanyinh.rpc.dto.Result;
import com.hanyinh.rpc.dto.req.RpcRequestDTO;
import com.hanyinh.rpc.dto.resp.RpcResponseDTO;
import com.hanyinh.rpc.proxy.JdkProxy;
import com.hanyinh.rpc.remote.RpcRemoteService;

/**
 * 客户端启动
 *
 * @author Hanyinh
 * @date 2020/9/20 11:34
 */
public class RpcClientApplication {

    public static void main(String[] args) {
        // 动态代理
        JdkProxy proxy = new JdkProxy();
        // 获取rpc接口
        RpcRemoteService service = (RpcRemoteService) proxy.getProxyInstance(RpcRemoteService.class);
        // 入参
        RpcRequestDTO req = new RpcRequestDTO();
        req.setCode("123456");
        req.setMobile("13003915143");
        // 调用具体方法
        Result<RpcResponseDTO> re = service.checkCode(req);
        System.out.println("re = " + JSON.toJSONString(re));
    }
}
