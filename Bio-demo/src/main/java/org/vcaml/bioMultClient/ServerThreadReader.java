package org.vcaml.bioMultClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThreadReader extends Thread{
    private Socket socket;

    public ServerThreadReader(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

            String msg;

            while((msg =bufferedReader.readLine())!= null){
                System.out.println("服务端按行读取接收到当前线程："+ Thread.currentThread().getName()+":"+ msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
