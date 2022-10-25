package com.example.android.myapplication.transport;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.security.auth.x500.X500Principal;

public class KeyManager {

    public static void generateCert(String keyAlias) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        final KeyPairGenerator kpg =
                KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");


        final KeyGenParameterSpec.Builder keyGenParameterSpecBuilder =
                new KeyGenParameterSpec.Builder(
                        keyAlias, KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                        .setDigests(KeyProperties.DIGEST_SHA384)
                        .setKeySize(256)
                        .setCertificateSerialNumber(new BigInteger(128, new SecureRandom()))
                        .setCertificateSubject(new X500Principal("CN=" + "littledog"));

        kpg.initialize(keyGenParameterSpecBuilder.build());
        kpg.generateKeyPair();
    }

    public static KeyStore.PrivateKeyEntry getKeyPair(String keyAlias) throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException {
        KeyStore keystore = KeyStore.getInstance("AndroidKeyStore");
        keystore.load(null);
        return (KeyStore.PrivateKeyEntry) keystore.getEntry(keyAlias, null);
    }
}
