package com.hanyinh.rpc.discovery;

/**
 * 服务发现
 *
 * @author Hanyinh
 * @date 2020/10/24 15:10
 */
public interface ServiceDiscovery {

    /**
     *从zk上发现服务
     * @param serviceName   com.hanyinh.rpc.remote.RpcRemoteService
     * @return
     */
    String discover(String serviceName);
}
