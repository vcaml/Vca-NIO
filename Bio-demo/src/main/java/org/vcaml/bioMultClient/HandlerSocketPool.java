package org.vcaml.bioMultClient;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerSocketPool {

    private ExecutorService executorService;

    public HandlerSocketPool(int maxThread, int queueSize){

        /***
         *  public ThreadPoolExecutor(
         *  int corePoolSize,
         *  int maximumPoolSize,
         *  long keepAliveTime,
         *  TimeUnit unit,
         *  BlockingQueue<Runnable> workQueue) {
         */
        executorService = new ThreadPoolExecutor(
                3,
                maxThread,
                120,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

   public void execute(Runnable task){
        executorService.execute(task);
   }
}
