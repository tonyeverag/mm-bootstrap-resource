package com.everag.mobilemanifest.bootstrap.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.lang.Assert;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class SigningKeyResolver extends SigningKeyResolverAdapter {

    private String secretKey;
    private String keyFromAuthorizationServer;

    public SigningKeyResolver(String secretKey, String keyFromAuthorizationServer) {
        this.secretKey = secretKey;
        this.keyFromAuthorizationServer = keyFromAuthorizationServer;
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        SignatureAlgorithm alg = SignatureAlgorithm.forName(header.getAlgorithm());
        if (alg.isHmac()) {
            return new SecretKeySpec(TextCodec.BASE64.decode(secretKey), alg.getJcaName());
        } else if (alg.isRsa()) {
            return RsaKeyHelper.parsePublicKey(keyFromAuthorizationServer);
        }

        Assert.isTrue(false);
        return null;
    }
}
