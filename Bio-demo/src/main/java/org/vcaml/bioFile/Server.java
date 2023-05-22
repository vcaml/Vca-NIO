package org.vcaml.bioFile;

import org.vcaml.bioMultClient.HandlerSocketPool;
import org.vcaml.bioMultClient.ServerRunnableTask;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);

            HandlerSocketPool pool = new HandlerSocketPool(6,10);
            while (true){
                Socket socket = serverSocket.accept();
                Runnable task = new FileUpRunnableTask(socket);
                pool.execute(task);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
