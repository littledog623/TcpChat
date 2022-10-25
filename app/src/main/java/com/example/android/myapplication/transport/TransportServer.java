package com.example.android.myapplication.transport;

import android.security.keystore.KeyProperties;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class TransportServer {

    private ServerSocket serverSocket;
    private Socket socket;

    public TransportServer(int port) {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore keystore = KeyStore.getInstance("AndroidKeyStore");
            keystore.load(null);
            tmf.init(keystore);

            Log.e("hojiang", "trustManager: " + tmf.getTrustManagers()[0].toString());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keystore, null);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

//            serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(port);

            serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);

            new Thread(() -> {
                try {
                    socket = serverSocket.accept();
                    Log.e("hojiang", "server start accept: " + socket.getPort());
                } catch (IOException e) {
                    Log.e("hojiang", "server accept exception: " + e);
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            Log.e("hojiang", "TransportServer exception: " + e);
        }
    }

    public void read() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                byte[] buffer = new byte[200];
                int redcount = socket.getInputStream().read(buffer);
                Log.e("hojiang", "server read: " + redcount + ", " + new String(ByteBuffer.wrap(buffer, 0, 4).array()));
            } catch (IOException | InterruptedException e) {
                Log.e("hojiang", "server accept exception: " + e);
            }
        }).start();
    }


}
