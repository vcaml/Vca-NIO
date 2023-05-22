package org.vcaml.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelCopyDemo {
    public static void main(String[] args) {
        try {
            File srcFile = new File("D:\\JavaProject\\fileTestPath\\client\\preview.jpg");
            File destFile = new File("D:\\JavaProject\\fileTestPath\\server\\preview.jpg");

            FileInputStream fileInputStream = new FileInputStream(srcFile);
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);

            FileChannel srsChannel = fileInputStream.getChannel();
            FileChannel destChannel = fileOutputStream.getChannel();

            //缓冲区是channel与实际数据的中转站
            ByteBuffer buffer =ByteBuffer.allocate(1024);

            while (true){
                buffer.clear();
                int flag = srsChannel.read(buffer);
                if(flag==-1){
                    break;
                }
                buffer.flip();
                destChannel.write(buffer);
            }

            srsChannel.close();
            destChannel.close();
            System.out.println("文件复制已完成");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
