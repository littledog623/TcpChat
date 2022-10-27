package com.example.android.myapplication.transport;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;

import javax.security.auth.x500.X500Principal;

public class KeyManager {

    public static KeyPair keyPair = null;

    public static void generateCert(String keyAlias) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
//        final KeyPairGenerator kpg =
//                KeyPairGenerator.getInstance("ECDSA", "BC");
//
//        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256k1");

        final KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
        final KeyGenParameterSpec.Builder keyGenParameterSpecBuilder =
                new KeyGenParameterSpec.Builder(
                        keyAlias, KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                        .setDigests(KeyProperties.DIGEST_NONE)
                        .setKeySize(384)
                        .setCertificateSerialNumber(new BigInteger(128, new SecureRandom()))
                        .setCertificateSubject(new X500Principal("CN=" + "littledog"));
        KeyGenParameterSpec spec = keyGenParameterSpecBuilder.build();
        kpg.initialize(spec);
        keyPair = kpg.generateKeyPair();
    }

    public static X509Certificate getCert(String keyAlias) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        return (X509Certificate) keyStore.getCertificate(keyAlias);
    }

    public static Certificate[] getCertChain(String keyAlias) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        return keyStore.getCertificateChain(keyAlias);
    }

    public static KeyStore getKeyStoreFromFile(Context context) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

        // get user password and file input stream
        try (InputStream fis = context.getAssets().open("littledog.keystore")) {
            ks.load(fis, null);
        }

        return ks;
    }
}
