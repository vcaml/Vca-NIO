package org.vcaml.bioBasicDemo;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端接受消息
 * */
public class Server {
    public static void main(String[] args) {
        //注册一个9999端口号
        try {
            System.out.println("服务端启动");
            ServerSocket serverSocket = new ServerSocket(9999);
            //监听客户的socket连接
            Socket socket = serverSocket.accept();
            //我们得到一个字节输入流对象（从这个流中读取客户端发来的数据）
            InputStream inputStream = socket.getInputStream();
            //通信规则 客户端和服务端要商量好 对方怎么发 这边怎么收 这里按行读取数据
            //用一个缓存字符流包装
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

            //定义按行读取
            String msg;
            while((msg =bufferedReader.readLine())!= null){
                System.out.println("服务端按行读取接收到"+msg);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
