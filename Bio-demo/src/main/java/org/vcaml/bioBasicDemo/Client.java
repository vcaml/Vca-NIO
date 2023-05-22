package org.vcaml.bioBasicDemo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端发送消息
 * */
public class Client {
    public static void main(String[] args) {
        //创建socket
        try {
            Socket socket = new Socket("127.0.0.1",9999);
            //获取一个字节输出流，刚才服务端是输入流读数据 这里就是输出流写数据
            OutputStream outputStream = socket.getOutputStream();
            //把字节流包装成一个高校简单的打印流
            PrintStream printStream = new PrintStream(outputStream);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("请说：");
                String msg = scanner.nextLine();
                printStream.println(msg);
                printStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
