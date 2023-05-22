package org.vcaml.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelPutDemo {
    public static void main(String[] args) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\JavaProject\\vca-IO\\NIO-demo\\data01.txt");

            FileChannel fileChannel = fileOutputStream.getChannel();

            //缓冲区是channel与实际数据的中转站
            ByteBuffer buffer =ByteBuffer.allocate(1024);

            buffer.put("阿根廷世界杯冠军".getBytes());

            buffer.flip();

            fileChannel.write(buffer);

            fileChannel.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
