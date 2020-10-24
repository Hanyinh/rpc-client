package com.hanyinh.rpc.discovery;

import java.util.List;
import java.util.Random;

/**
 * 负载均衡实现
 *
 * @author Hanyinh
 * @date 2020/10/24 15:22
 */
public class RandomLoadBalance implements LoadBalance {

    @Override
    public String select(List<String> nodeList) {
        int size = nodeList.size();
        if (size == 0) {
            return null;
        }
        Random random = new Random();
        // 随机选择一个 最简单的负载均衡算法
        return nodeList.get(random.nextInt(size));
    }
}
