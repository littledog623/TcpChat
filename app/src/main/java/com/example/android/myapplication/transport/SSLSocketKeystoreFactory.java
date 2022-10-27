package com.example.android.myapplication.transport;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLSocketKeystoreFactory {

    private static String instance;
    /**
     * CONFIGURATION SECTION
     */
    static {
        instance = "AndroidKeyStore"/* TODO REPLACE WITH BKS IF USING IT*/;
        /*
         * Several Notes:
         * - Android only works with BKS, so you need to use only BKS certs files
         * - As before Android 15, BKS-v1 was used, you need to convert BKS in BKS-v1 to use it in Android 15-; BUT as Android 23+ doesn't support BKS-v1
         * and as BKS-v1 is deprecated, you need to have both of the certs and use them in fuction of the version
         * - Java doesn't support BKS without library
         * - A BKS format client can be connected a JKS format server
         */
    }

    /**
     *
     * A SSL algorithms types chooser enum
     *
     * @author gpotter2
     *
     */
    public static enum SecureType {
        TLS("TLS"),
        TLSv1_2("TLSv1.2");

        private String type;

        private SecureType(String type){
            this.type = type;
        }
        public String getType(){
            return type;
        }
    }

    /**
     * Instantiate sslsocket
     *
     * @param ip The IP to connect the socket to
     * @param port The port of the socket
     * @param pathToCert The path to the KeyStore cert (can be with getClass().getRessourceAsStream()....)
     * @param passwordFromCert The password of the KeyStore cert
     * @param type The SSL algorithm to use
     * @return The SSLSocket or null if the connection was not possible
     * @throws IOException If the socket couldn't be created
     * @throws KeyManagementException  If the KeyManager couldn't be loaded
     * @throws CertificateException If the certificate is not correct (null or damaged) or the password is incorrect
     * @throws NoSuchAlgorithmException If the certificate is from an unknown type
     * @throws KeyStoreException If your system is not compatible with JKS KeyStore certificates
     * @author gpotter2
     */
    public static SSLSocket getSocketWithCert(Context context, String ip, int port, SecureType type) throws IOException,
            KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, SocketException{
        InetAddress ip2 = InetAddress.getByName(ip);
        if(ip2 == null){
            new NullPointerException("The ip must be a correct IP !").printStackTrace();
            return null;
        }
        return getSocketWithCert(context, ip2, port, type);
    }

    /**
     * Instantiate sslsocket
     *
     * @param ip The IP to connect the socket to
     * @param port The port of the socket
     * @param pathToCert The path to the KeyStore cert (can be with getClass().getRessourceAsStream()....)
     * @param passwordFromCert The password of the KeyStore cert
     * @param type The SSL algorithm to use
     * @return The SSLSocket or null if the connection was not possible
     * @throws IOException If the socket couldn't be created
     * @throws KeyManagementException  If the KeyManager couldn't be loaded
     * @throws CertificateException If the certificate is not correct (null or damaged) or the password is incorrect
     * @throws NoSuchAlgorithmException If the certificate is from an unknown type
     * @throws KeyStoreException If your system is not compatible with JKS KeyStore certificates
     * @author gpotter2
     */
    public static SSLSocket getSocketWithCert(Context context, InetAddress ip, int port, SecureType type) throws IOException,
            KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, SocketException{
        X509TrustManager[] tmm;
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);
        tmm=tm(ks);
        SSLContext ctx = SSLContext.getInstance(type.getType());
        ctx.init(null, tmm, null);

        SSLSocketFactory SocketFactory = (SSLSocketFactory) ctx.getSocketFactory();
        SSLSocket socket = (SSLSocket) SocketFactory.createSocket(ip, port);
        return socket;
    }

    /**
     * Util class to get the X509TrustManager
     *
     *
     * @param keystore
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @author gpotter2
     */
    private static X509TrustManager[] tm(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory trustMgrFactory = TrustManagerFactory.getInstance("X509");
        trustMgrFactory.init(keystore);
        TrustManager[] trustManagers = trustMgrFactory.getTrustManagers();
        for (int i = 0; i < trustManagers.length; i++) {
            if (trustManagers[i] instanceof X509TrustManager) {
                X509TrustManager[] tr = new X509TrustManager[1];
                tr[0] = (X509TrustManager) trustManagers[i];
                return tr;
            }
        }
        return null;
    };
}
