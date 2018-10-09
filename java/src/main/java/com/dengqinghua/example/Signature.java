package com.dengqinghua.example;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Comparator;
import java.util.Map;

public class Signature {
    String privateKey, publicKey;
    public Signature(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String sign(Map<String, String> data) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        PrivateKey privateK = KeyFactory.getInstance("RSA").generatePrivate(pkcs8KeySpec);
        java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateK);

        String stringData = mapToString(data);
        System.out.println(stringData);
        signature.update(stringData.getBytes("utf-8"));
        return Base64.encodeBase64String(signature.sign());
    }

    public boolean verify(Map<String, String> data) throws Exception {
        String orgSign = data.get("sign");
        String stringSignTemp = mapToString(data);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
        signature.initVerify(publicK);
        signature.update(stringSignTemp.getBytes("utf-8"));
        return signature.verify(Base64.decodeBase64(orgSign));
    }

    private String mapToString(Map<String, String> data) {
        return data.entrySet().stream().
                filter(x -> !"sign".equals(x.getKey())).
                sorted(Comparator.comparing(Map.Entry::getKey))
                .map(p -> p.getKey() + "=" + p.getValue())
                .reduce((k, v) -> k + "&" + v)
                .orElse("");
    }
}
