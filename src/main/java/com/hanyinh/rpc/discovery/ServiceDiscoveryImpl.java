package com.hanyinh.rpc.discovery;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务发现实现
 *
 * @author Hanyinh
 * @date 2020/10/24 15:11
 */
@Slf4j
public class ServiceDiscoveryImpl implements ServiceDiscovery {

    private List<String> nodeList = new ArrayList<>();

    /**
     * 连接zk
     */
    private CuratorFramework curatorFramework;

    public ServiceDiscoveryImpl() {
        init();
    }

    public void init() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZkConfig.CONNECTION_STR)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
    }

    @Override
    public String discover(String serviceName) {
        String path = ZkConfig.ZK_REGISTER_PATH + serviceName;
        try {
            // 订阅   获取服务对应的节点列表
            nodeList = curatorFramework.getChildren().forPath(path);
        } catch (Exception e) {

        }
        // 监听   利用zk的心跳机制，提供某个节点下面节点变化情况
        registerWatch(path);
        // 负载均衡的算法
        LoadBalance loadBalance = new RandomLoadBalance();
        return loadBalance.select(nodeList);
    }

    /**
     * 监听
     * @param path
     */
    private void registerWatch(String path) {
        PathChildrenCache cache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                // 监听事件，有变化时更新
                nodeList = curatorFramework.getChildren().forPath(path);
            }
        };
        cache.getListenable().addListener(listener);
        try {
            cache.start();
        } catch (Exception e) {
            log.error("监听Path{}异常：{}", path, e);
        }
    }
}
