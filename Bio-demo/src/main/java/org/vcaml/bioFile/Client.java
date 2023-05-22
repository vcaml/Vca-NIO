package org.vcaml.bioFile;


import javax.imageio.IIOException;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 *
 * 客户端上传任意文件
 * */
public class Client {
    public static void main(String[] args) {

    try{
        Socket socket = new Socket("127.0.0.1", 8888);

        //包装字节输出流
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

//        //先发送文件后缀
//        dataOutputStream.writeUTF(".jpg");
//        //先从客户端本地读取
//        InputStream inputStream = new FileInputStream("D:\\JavaProject\\fileTestPath\\client\\preview.jpg");

        dataOutputStream.writeUTF(".txt");
        //先从客户端本地读取
        InputStream inputStream = new FileInputStream("D:\\JavaProject\\fileTestPath\\client\\待完成博客目录.txt");

        byte[] buffer = new byte[1024];

        int len;

        while ((len=inputStream.read(buffer))> 0) {

            dataOutputStream.write(buffer,0,len);
        }

        dataOutputStream.flush();

        socket.shutdownOutput();//通知服务端已经发送完毕了
    }catch (IOException e){
         e.printStackTrace();
    }
   }
}
