package com.hanyinh.rpc.discovery;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务发现
 *
 * @author Hanyinh
 * @date 2020/10/24 15:49
 */
@Slf4j
public class DiscoveryTest {

    public static void main(String[] args) {
        ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl();
        String serviceAddress = serviceDiscovery.discover("com.hanyinh.rpc.remote.RpcRemoteService");
        log.error("服务地址：{}", serviceAddress);
    }
}
