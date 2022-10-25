package com.example.android.myapplication.transport;

import android.util.Log;

import java.io.IOException;
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

    private Socket clientSocket;

    public TransportClient(String hostname, int port) throws Exception {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore keystore = KeyStore.getInstance("AndroidKeyStore");
            keystore.load(null);
            tmf.init(keystore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            clientSocket = new Socket(hostname, port);
            clientSocket.setSoTimeout(10000);
        } catch (Exception e) {
            throw e;
        }
    }

    public void sendMessage(String message) throws IOException {
        try {
            Log.e("hojiang", "client sendMessage");
            clientSocket.getOutputStream().write(message.getBytes());
        } catch (IOException e) {
            throw e;
        }
    }
}
