package org.vcaml.bioFile;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class FileUpRunnableTask implements Runnable{
    private Socket socket;

    public FileUpRunnableTask(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try{
            DataInputStream dataInputStream =new DataInputStream(socket.getInputStream());
            //先读取文件类型 客户端是  dataOutputStream.writeUTF(".jpg"); 这里要严格对应
            String suffix = dataInputStream.readUTF();

            System.out.println("服务端接收到文件类型"+suffix);

            OutputStream outputStream = new FileOutputStream("D:\\JavaProject\\fileTestPath\\server\\"+
                    UUID.randomUUID().toString()+suffix);

            byte[] buffer = new byte[1024];
            int len;
            while ((len=dataInputStream.read(buffer))> 0) {
                outputStream.write(buffer,0,len);
            }
            outputStream.close();
            System.out.println("服务端保存文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
