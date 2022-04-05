package com.handwritten.nio;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost",8000);
        socket.getOutputStream().write("test".getBytes(StandardCharsets.UTF_8));
        Thread.sleep(1000000);
    }
}
