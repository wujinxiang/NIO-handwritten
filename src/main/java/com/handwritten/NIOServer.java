package com.handwritten;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NIOServer {
    static List<SocketChannel> channels = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8000));
        //set ServerSocketChannel as non block
        serverSocketChannel.configureBlocking(false);
        System.out.println("NIO server start...");

        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                System.out.println("Connected...");
                //set socketChannel as non block
                socketChannel.configureBlocking(false);
                channels.add(socketChannel);
            }
            //loop SocketChannel list
            Iterator<SocketChannel> iterator = channels.iterator();
            while(iterator.hasNext()){
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                int len = sc.read(byteBuffer);
                if(len > 0){
                    System.out.println("Receive messages: " + new String(byteBuffer.array()));
                } else if(len == -1){//if client dropped off, remove it from SocketChannel list
                    iterator.remove();
                    System.out.println("Client drop off...");
                }
            }
        }
    }
}
