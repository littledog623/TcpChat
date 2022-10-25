package com.example.android.myapplication;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public void startServer() {
        Log.e("hojiang", "startServer");
        try {
            ServerSocket listeningSocket = new ServerSocket();
            listeningSocket.bind(new InetSocketAddress("localhost", 33333));
            Log.e("hojiang", "listening...");
            Socket clientSocket = listeningSocket.accept();
        } catch (IOException e) {
            Log.e("hojiang", "Socket connect error: " + e);
        }
    }
}
