package com.handwritten;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        while(true){
            System.out.println("Waiting...");
            //block method
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected...");
            //C10k C10M 问题，短时间大量并发请求进来需要创建大量的线程，导致服务器内存爆掉
            //如果用线程池来优化，每次处理的并发请求就是线程池的工作线程数量
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            /*
            * int corePoolSize,
              int maximumPoolSize,
              long keepAliveTime,
              TimeUnit unit,
              BlockingQueue<Runnable> workQueue,
              ThreadFactory threadFactory,
              RejectedExecutionHandler handler
            */
//            new ThreadPoolExecutor(2, 4, 5000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), );
        }
    }

    private static void handler(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("Read message start..");
        int message = clientSocket.getInputStream().read(bytes);
        System.out.println("Read message completed..");
        if(message != -1){
            System.out.println("Receive message: " + new String(bytes, 0, message));
        }
    }
}