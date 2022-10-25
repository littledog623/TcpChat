package com.example.android.myapplication.chatapi;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.Pipe;
import java.nio.channels.SocketChannel;

public class PipelineSocket implements IPipelineSocket {

    private Socket socket;

    private Pipe inputPipe;
    private Pipe outputPipe;

    private int maxMessageSize;

    private SocketAddress remoteEndpoint;

    public PipelineSocket(Socket connectedSocket, int maxMessageSize) {
        this.socket = connectedSocket;
        this.maxMessageSize = maxMessageSize;
        this.remoteEndpoint = socket.getRemoteSocketAddress();
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    @Override
    public SocketAddress getRemoteEndpoint() {
        return remoteEndpoint;
    }
}
