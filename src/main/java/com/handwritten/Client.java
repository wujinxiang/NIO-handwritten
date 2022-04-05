package com.handwritten;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8000);
        socket.getOutputStream().write("test".getBytes(StandardCharsets.UTF_8));
    }
}
