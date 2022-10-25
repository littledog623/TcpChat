package com.example.android.myapplication.chatapi;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public interface IPipelineSocket {

    Socket getSocket();

    int getMaxMessageSize();

    SocketAddress getRemoteEndpoint();
}
