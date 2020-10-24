package com.hanyinh.rpc.client.socket;

import com.hanyinh.rpc.common.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Socket方式客户端通信
 *
 * @author Hanyinh
 * @date 2020/9/20 11:40
 */
@Slf4j
public class RpcClient {

    private static final String IP = "127.0.0.1";

    private static final Integer PORT = 8083;


    public Object start(RpcRequest rpcRequest) {
        // 连接socket
        Socket socket = getSocket();
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            // 客户端向服务端发送数据
            oos.writeObject(rpcRequest);
            oos.flush();
            // 客户端获取服务端返回的数据
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }

    private Socket getSocket() {
        try {
            Socket socket = new Socket(IP, PORT);
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
