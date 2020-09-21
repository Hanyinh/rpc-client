package com.hanyinh.rpc.proxy;

import com.hanyinh.rpc.client.RpcClient;
import com.hanyinh.rpc.common.RpcRequest;
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
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParams(args);
        rpcRequest.setTypes(method.getParameterTypes());
        // 客户端通信
        RpcClient client = new RpcClient();
        Object result = client.start(rpcRequest);
        return result;
    }

    public Object getProxyInstance(Class clazz) {
        // 接口的话可以直接使用   new Class<?>[]{clazz}
        // 如果是实现类可以使用   clazz.getInterfaces()
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }
}
