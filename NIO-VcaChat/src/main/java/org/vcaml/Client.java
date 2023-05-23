package org.vcaml;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 群聊系统客户端
 */

public class Client {
    private Selector selector;

    private SocketChannel socketChannel;

    private static final int PORT = 9999;

    public Client(){
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9999));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("当前客户端就绪：");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();

        //定义一个线程专门负责监听服务端发过来的读消息事件
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.readFromServer();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("主线程发消息-请说：");
            String msg = scanner.nextLine();
            client.sendToServer(msg);
        }

    }

    private void sendToServer(String msg) {
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void readFromServer() throws IOException {
        System.out.println("副线程收消息：");
        //监听其他客户端发来的消息
        while (selector.select()>0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey selectionKey =it.next();
                //判断一下这个事件是什么
             if (selectionKey.isReadable()){
                    SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len;
                    while ((len = clientChannel.read(buffer))>0){
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }
                }
                it.remove();
            }
        }
    }
}
