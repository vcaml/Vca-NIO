package org.vcaml.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO非阻塞通道下的通信实现：服务端
 * */
public class Server {
    public static void main(String[] args) throws IOException {
        //获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //切换为非阻塞模式
        ssChannel.configureBlocking(false);
        //绑定连接端口
        ssChannel.bind(new InetSocketAddress(9999));
        //获取选择器
        Selector selector = Selector.open();
        //把通道绑定到选择器上去，指定监听事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started on port 9999");

        //选择器开始轮询是否存在就绪好有数据传送的事件
        while (selector.select()>0){
            //获取所有准备好的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey selectionKey =it.next();
                //判断一下这个事件是什么
                if(selectionKey.isAcceptable()){
                    System.out.println("检测到事件为连接事件");
                    SocketChannel channel = ssChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector,SelectionKey.OP_READ);
                }else if (selectionKey.isReadable()){
                    System.out.println("检测到事件为数据读写事件");
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
