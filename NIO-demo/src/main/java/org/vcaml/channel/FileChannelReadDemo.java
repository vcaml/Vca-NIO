package org.vcaml.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelReadDemo {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\JavaProject\\vca-IO\\NIO-demo\\data01.txx");

            FileChannel fileChannel = fileInputStream.getChannel();

            //缓冲区是channel与实际数据的中转站
            ByteBuffer buffer =ByteBuffer.allocate(1024);
            //上面这部分 读文件和写文件 完全是一样的，可以看出channel是双向的

            fileChannel.read(buffer);

            //移动到缓冲区的第一个位置
            buffer.flip();

            String ss =new String(buffer.array(),0,buffer.remaining());

            System.out.println(ss);

            fileChannel.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
