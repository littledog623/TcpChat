package com.example.android.myapplication.chatapi;

import com.example.android.myapplication.chatapi.messages.IMessage;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;

public class ChatConnection {

    private SocketChannel socketChannel;

    public ChatConnection(SocketAddress address) {
        try {
            Channel channel;
            socketChannel = SocketChannel.open();
            socketChannel.connect(address);
        } catch (IOException e) {

        }
    }

    public void sendMessage(IMessage message) {
        try {
            socketChannel.write(message.toByteBuffer());
        } catch (IOException e) {

        }
    }
}
