package com.mariodicaprio.mamba.security;


import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


@Configuration
public class RSAKeyConfig {

    private byte[] readPemFile(String classpath) throws Exception {
        File pem = new ClassPathResource(classpath).getFile();
        FileReader fileReader = new FileReader(pem);
        PemReader pemReader = new PemReader(fileReader);
        PemObject pemObject = pemReader.readPemObject();
        return pemObject.getContent();
    }

    @Bean
    RSAPublicKey publicKey() throws Exception {
        byte[] content = readPemFile("cert/public.pem");
        KeySpec keySpec = new X509EncodedKeySpec(content);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) factory.generatePublic(keySpec);
    }

    @Bean
    RSAPrivateKey privateKey() throws Exception {
        byte[] content = readPemFile("cert/private.pem");
        KeySpec keySpec = new PKCS8EncodedKeySpec(content);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) factory.generatePrivate(keySpec);
    }

}
