package com.hanyinh.rpc.proxy;

import com.hanyinh.rpc.client.netty.NettyClient;
import com.hanyinh.rpc.common.RpcRequest;
import com.hanyinh.rpc.discovery.ServiceDiscovery;
import com.hanyinh.rpc.discovery.ServiceDiscoveryImpl;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类
 *
 * @author Hanyinh
 * @date 2020/9/20 12:00
 */
@Slf4j
public class JdkProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 调用的类名、方法名、参数、参数类型
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(serviceName);
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setTypes(method.getParameterTypes());
        rpcRequest.setParams(args);

        // 方式一：Socket客户端通信
        // RpcClient client = new RpcClient();
        // Object result = client.start(rpcRequest);

        // 方式二：Netty客户端通信
        NettyClient client = new NettyClient();
        ServiceDiscovery discovery = new ServiceDiscoveryImpl();
        String serviceAddress = discovery.discover(serviceName);
        String[] address = serviceAddress.split(":");
        String host = address[0];
        int port = Integer.parseInt(address[1]);
        Object result = client.start(host, port, rpcRequest);

        return result;
    }

    public Object getProxyInstance(Class clazz) {
        // 接口的话可以直接使用   new Class<?>[]{clazz}
        // 如果是实现类可以使用   clazz.getInterfaces()
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }
}
