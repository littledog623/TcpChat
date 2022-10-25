package com.example.android.myapplication;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Pipe;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatClient {

    private Socket clientSocket;

    public void startConnect() {
        Log.e("hojiang", "client start connect...");
        try {
            clientSocket = SocketChannel.open(new InetSocketAddress("10.106.152.76", 33333)).socket();
            Log.e("hojiang", "Client connected from " + clientSocket.getInetAddress() + ", port " + clientSocket.getLocalPort());
        } catch (IOException e) {
            Log.e("hojiang", "client connect error: " + e);
        }
    }

    public void sendMessage(String message) {
        try {
            byte[] messageBytes = message.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(messageBytes.length + 4 + 4 + 2);
            buffer.order(ByteOrder.BIG_ENDIAN);

            buffer.putInt(messageBytes.length);
            buffer.putInt(0);
            buffer.putShort((short) messageBytes.length);
            buffer.put(messageBytes);
            buffer.flip();
            Log.e("hojiang", "sendMessage buffer: " + buffer.remaining());
            clientSocket.getChannel().write(buffer);
            Selector selector = Selector.open();
            Pipe pipe = Pipe.open();
//            clientSocket.getOutputStream().write(array);
        } catch (IOException e) {
            Log.e("hojiang", "sendMessage exception: " + e);
        }
    }
}
