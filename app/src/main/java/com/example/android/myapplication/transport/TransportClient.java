package com.example.android.myapplication.transport;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class TransportClient {

    private SSLSocket clientSocket;

    public TransportClient(Context context, String hostname, int port) throws Exception {
        try {
            clientSocket = SSLSocketKeystoreFactory.getSocketWithCert(context, hostname, port, SSLSocketKeystoreFactory.SecureType.TLSv1_2);
//            clientSocket.setEnabledCipherSuites(clientSocket.getSupportedCipherSuites());
            Log.e("hojiang", "client connected: " + clientSocket.isConnected() + ", " + clientSocket.getLocalAddress().getHostAddress() + ", " + clientSocket.getLocalPort());
            ((SSLSocket) clientSocket).addHandshakeCompletedListener(new HandshakeCompletedListener() {
                @Override
                public void handshakeCompleted(HandshakeCompletedEvent event) {
                    Log.e("hojiang", "handshake complete");
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public void doHandshake() throws IOException {
        ((SSLSocket) clientSocket).startHandshake();
        Log.e("hojiang", "handshake done");
    }

    public void sendMessage(String message) throws IOException {
        try {
            Log.e("hojiang", "client sendMessage");
            OutputStream outputStream = clientSocket.getOutputStream();
            Log.e("hojiang", "client get outputstream");
            outputStream.write(message.getBytes());
            Log.e("hojiang", "client message sent");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
