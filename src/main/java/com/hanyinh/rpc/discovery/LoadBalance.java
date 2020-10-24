package com.hanyinh.rpc.discovery;

import java.util.List;

/**
 * 负载均衡
 *
 * @author Hanyinh
 * @date 2020/10/24 15:20
 */
public interface LoadBalance {

    /**
     * 负载均衡
     * 从多个服务中选择一个返回
     * @param nodeList
     * @return
     */
    String select(List<String> nodeList);
}
