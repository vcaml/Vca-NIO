package org.vcaml.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class transferFromAndToWithChannel {
    public static void main(String[] args) {

        try {
            File srcFile = new File("D:\\JavaProject\\fileTestPath\\client\\data01.txt");
            File destFile = new File("D:\\JavaProject\\fileTestPath\\server\\data01.txt");

            FileInputStream fileInputStream = new FileInputStream(srcFile);
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);

            FileChannel srsChannel = fileInputStream.getChannel();
            FileChannel destChannel = fileOutputStream.getChannel();

            //不需要buffer 之间在通道之前复制数据
            destChannel.transferFrom(srsChannel,srsChannel.position(),srsChannel.size());

            srsChannel.close();
            destChannel.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
