package org.vcaml.bioMultClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 实现服务端同时接收多个客户端socket通信需求
 * */
public class Sever {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);

            HandlerSocketPool pool = new HandlerSocketPool(6,10);
            while (true) {
                Socket socket = serverSocket.accept();
                //new ServerThreadReader(socket).start();
                //优化之前的线程创建 将socket包装成任务对象 交给线程池
                Runnable task = new ServerRunnableTask(socket);
                pool.execute(task);
            }
        } catch (IOException e) {
             e.printStackTrace();
        }

    }
}
