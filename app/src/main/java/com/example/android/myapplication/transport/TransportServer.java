package com.example.android.myapplication.transport;

import android.content.Context;
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

    private SSLServerSocket serverSocket;
    private SSLSocket socket;

    public TransportServer(Context context, int port) {
        try {
//            serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(port);

            serverSocket = SSLServerSocketKeystoreFactory.getServerSocketWithCert(context, port, SSLServerSocketKeystoreFactory.ServerSecureType.TLSv1_2);
//            serverSocket.setEnabledCipherSuites(serverSocket.getSupportedCipherSuites());

            try {
                socket = (SSLSocket) serverSocket.accept();
                Log.e("hojiang", "server start accept: " + socket.getLocalPort());
                socket.startHandshake();
            } catch (IOException e) {
                Log.e("hojiang", "server accept exception: " + e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            Log.e("hojiang", "TransportServer exception: " + e);
            e.printStackTrace();
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
