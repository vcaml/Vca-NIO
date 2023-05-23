package org.vcaml;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 服务端群聊系统实现
 * */
public class Server {

    //定义一些选择器 通道 端口

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private static final int PORT = 9999;

    public Server(){
        //接受选择器
        try {
            //接受选择器
            selector =Selector.open();

            serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.bind(new InetSocketAddress(PORT));

            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listen() {
        try{
           while (selector.select()>0){
               //获取所有准备好的事件
               Iterator<SelectionKey> it = selector.selectedKeys().iterator();

               while (it.hasNext()) {
                   SelectionKey selectionKey =it.next();
                   //判断一下这个事件是什么
                   if(selectionKey.isAcceptable()){
                       SocketChannel channel = serverSocketChannel.accept();
                       channel.configureBlocking(false);
                       channel.register(selector,SelectionKey.OP_READ);
                   }else if (selectionKey.isReadable())
                   {
                       //处理客户端的消息 把它转发给其他客户端

                       readClientData(selectionKey);
                   }
                   it.remove();
               }

           }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 接受当前客户端通道的信息，转发给其他客户端
     * @param selectionKey
     */
    private void readClientData(SelectionKey selectionKey) {
        SocketChannel clientChannel = null;

        try {
            clientChannel = (SocketChannel) selectionKey.channel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = clientChannel.read(buffer);

            if (count>0){
                buffer.flip();
                String msg = new String(buffer.array(),0,count);
                System.out.println("接收到客户端消息"+msg);
                sentToAllClient(msg,clientChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println("有人离线了"+clientChannel.getRemoteAddress());
                selectionKey.cancel();
                serverSocketChannel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    /**
     * 把当前客户端的消息数据 推送给当前 全部在线注册的channel
     * @param msg
     * @param clientChannel
     */
    private void sentToAllClient(String msg, SocketChannel clientChannel) throws IOException{

        System.out.println("--服务端转发当前客户端消息给其他所有在线注册的客户端--");

        System.out.println("当前处理线程为:"+Thread.currentThread().getName());

        for (SelectionKey key:selector.keys()){

            Channel channel = key.channel();

            //不要发数据发给自己： 判断遍历所有的通道时 当前通道是不是当前客户端的同一个通道
            if (channel instanceof SocketChannel && channel != clientChannel){
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel)channel).write(buffer);
            }
        }

    }


    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }

}
