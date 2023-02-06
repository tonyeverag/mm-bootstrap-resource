package com.everag.mobilemanifest.bootstrap.interfaces.web.rest.jwt;

import com.everag.mobilemanifest.bootstrap.domain.model.security.ImpersonatedUser;
import com.everag.mobilemanifest.bootstrap.domain.model.security.SaltyUser;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import org.apache.commons.lang.StringUtils;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenProvider implements InitializingBean {
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";
    private static final String IMPERSONATING_USER_ID = "impersonatedByUserId";
    private static final String OAUTH_AUTHORITIES_KEY = "authorities";
    private static final String LOAD_IDENTIFIERS_KEY = "loadIds";
    private static final String ACCESS_KEY = "access";

    @Value("${dairy.client-secret}")
    private String secretKey;

    @Deprecated
    @Value("${dairy.client-secret-deprecated}")
    private String oldSecretKey;

    @Value("${security.oauth2.client.access-token-validity-seconds:3600}")
    private long tokenValidityInSeconds;

    @Value("${security.oauth2.client.access-token-remember-me-validity-seconds:604800}")
    private long tokenValidityInSecondsForRememberMe;

    @Autowired
    private String getKeyFromAuthorizationServer;

    @Autowired
    private UserDetailsService userDetailsService;

    private final Map<Algorithm, ConfigurableJWTProcessor<SimpleSecurityContext>> jwtProcessorMap = new HashMap<>(3);

    @Override
    public void afterPropertiesSet() throws NoSuchAlgorithmException, InvalidKeySpecException {
        ConfigurableJWTProcessor<SimpleSecurityContext> localJwtProcessor = new DefaultJWTProcessor<>();
        localJwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector<>(JWSAlgorithm.HS256, new ImmutableSecret<>(secretKey.getBytes())));

        jwtProcessorMap.put(JWSAlgorithm.HS256, localJwtProcessor);

        ConfigurableJWTProcessor<SimpleSecurityContext> uaaJwtProcessor = new DefaultJWTProcessor<>();
        String publicKeyContent = getKeyFromAuthorizationServer.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey uaaKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
        RSAKey uaaJwk = new RSAKey.Builder(uaaKey)
                .algorithm(JWSAlgorithm.RS256)
                .build();
        JWKSet keys = new JWKSet(List.of(uaaJwk));
        ImmutableJWKSet<SimpleSecurityContext> keySet = new ImmutableJWKSet<>(keys);
        uaaJwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, keySet));
        jwtProcessorMap.put(JWSAlgorithm.RS256, uaaJwtProcessor);

        log.info("keytypes loaded: {}", StringUtils.join(jwtProcessorMap.keySet(), ","));
    }

    public String createToken(Authentication authentication, Boolean rememberMe) throws JOSEException {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInSecondsForRememberMe * 1000);
        } else {
            validity = new Date(now + this.tokenValidityInSeconds * 1000);
        }

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(((SaltyUser) authentication.getPrincipal()).getId().toString())
                .claim(AUTHORITIES_KEY, authorities)
                .expirationTime(validity)
                .build();
        JWSSigner signer = new MACSigner(secretKey);
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    public String createImpersonationToken(Authentication authentication, String impersonatingUserId) throws JOSEException{
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInSeconds * 1000);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(((SaltyUser) authentication.getPrincipal()).getId().toString())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(IMPERSONATING_USER_ID, impersonatingUserId)
                .expirationTime(validity)
                .build();
        JWSSigner signer = new MACSigner(secretKey);
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    public String createTokenForIntegration(Authentication authentication, Boolean rememberMe, List<String> loadIds) throws JOSEException {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String loads = String.join(",", loadIds);

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInSecondsForRememberMe * 1000);
        } else {
            validity = new Date(now + this.tokenValidityInSeconds * 1000);
        }

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(((SaltyUser) authentication.getPrincipal()).getId().toString())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(LOAD_IDENTIFIERS_KEY, loads)
                .expirationTime(validity)
                .build();
        JWSSigner signer = new MACSigner(secretKey);
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    public Authentication getAuthentication(String token) throws ParseException, JOSEException, BadJOSEException {
        JWT jwt = JWTParser.parse(token);

        if (JWSAlgorithm.HS512.equals(jwt.getHeader().getAlgorithm())) {
            // still support the old style until they aren't used anymore
            return getAuthenticationOld(token);
        }

        ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = jwtProcessorMap.get(jwt.getHeader().getAlgorithm());
        JWTClaimsSet claims = jwtProcessor.process(jwt, null);

        Collection<GrantedAuthority> authorities;

        if (claims.getClaim(LOAD_IDENTIFIERS_KEY) != null && !StringUtils.isEmpty(claims.getClaim(LOAD_IDENTIFIERS_KEY).toString())) {
            authorities = Stream.concat(
                            Arrays.stream(claims.getClaim(AUTHORITIES_KEY).toString().split(","))
                                    .map(SimpleGrantedAuthority::new),
                            Arrays.stream(claims.getClaim(LOAD_IDENTIFIERS_KEY).toString().split(","))
                                    .map(SimpleGrantedAuthority::new))
                    .collect(Collectors.toList());
        } else if (claims.getClaim(AUTHORITIES_KEY) != null) {
            authorities = Arrays.stream(claims.getClaim(AUTHORITIES_KEY).toString().split(","))
                    .map(auth -> {
                        String formattedString = StringUtils.trim(auth);
                        formattedString = StringUtils.remove(formattedString, '[');
                        formattedString = StringUtils.remove(formattedString, ']');
                        return formattedString;
                    }).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } else if (claims.getClaim(OAUTH_AUTHORITIES_KEY) instanceof JSONArray) {
            authorities = ((JSONArray) claims.getClaim(OAUTH_AUTHORITIES_KEY)).stream()
                    .map(Object::toString)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } else {
            authorities = new ArrayList<>();
        }

        if (claims.getClaim(ACCESS_KEY) instanceof List<?>) {
            ((List<Map<String, Object>>) claims.getClaim(ACCESS_KEY)).stream()
                    .filter(map -> "mmweb".equals(map.get("appIdentifier")))
                    .forEach(m -> {
                        if (m.get("externalId") != null && m.get("accessScope") != null && m.get("accessType") != null) {
                            try {
                                AccessAuthority auth = new AccessAuthority(Long.parseLong(m.get("externalId").toString()),
                                        AccessScope.valueOf(m.get("accessScope").toString()),
                                        AccessType.valueOf(m.get("accessType").toString()));
                                authorities.add(auth);
                            }
                            catch (Exception e) {
                                log.warn("Could not parse access authority from jwt: {}", m);
                            }
                        }
                    });
        }

        if (claims.getClaim(IMPERSONATING_USER_ID) != null){
            User principal = new ImpersonatedUser(Optional.ofNullable(claims.getSubject()).orElseGet(
                    () -> (String) Optional.ofNullable(claims.getClaim("client_id")).orElseGet(
                            () -> claims.getClaims().getOrDefault("user_name", "unknown"))),
                    "",
                    authorities,(String) claims.getClaim(IMPERSONATING_USER_ID));
            return new UsernamePasswordAuthenticationToken(principal, "", authorities);
        }

        User principal = new User(
                Optional.ofNullable(claims.getSubject()).orElseGet(
                        () -> (String) Optional.ofNullable(claims.getClaim("client_id")).orElseGet(
                                () -> claims.getClaims().getOrDefault("user_name", "unknown"))),
                "",
                authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    @Deprecated
    private Authentication getAuthenticationOld(String token) {
        log.warn("DEPRECATED old style getAuthentication");
        SigningKeyResolver signingKeyResolver =
                new com.everag.mobilemanifest.bootstrap.application.security.SigningKeyResolver(oldSecretKey, getKeyFromAuthorizationServer);
        Claims claims = Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(token)
                .getBody();

        Collection<GrantedAuthority> authorities;

        if(claims.get(LOAD_IDENTIFIERS_KEY) != null && !StringUtils.isEmpty(claims.get(LOAD_IDENTIFIERS_KEY).toString())) {
            authorities = Stream.concat(
                            Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream()
                                    .map(authority -> new SimpleGrantedAuthority(authority)),
                            Arrays.asList(claims.get(LOAD_IDENTIFIERS_KEY).toString().split(",")).stream()
                                    .map(authority -> new SimpleGrantedAuthority(authority)))
                    .collect(Collectors.toList());
        } else if (claims.get(AUTHORITIES_KEY) != null) {
            authorities = Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream()
                    .map(auth -> {
                        String formattedString = StringUtils.trim(auth);
                        formattedString = StringUtils.remove(formattedString, '[');
                        formattedString = StringUtils.remove(formattedString, ']');
                        return formattedString;
                    }).map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        } else if (claims.get(OAUTH_AUTHORITIES_KEY) != null) {
            authorities = Arrays.asList(claims.get(OAUTH_AUTHORITIES_KEY).toString().split(",")).stream()
                    .map(auth -> {
                        String formattedString = StringUtils.trim(auth);
                        formattedString = StringUtils.remove(formattedString, '[');
                        formattedString = StringUtils.remove(formattedString, ']');
                        return formattedString;
                    }).map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        } else {
            authorities = new ArrayList<>();
        }

        if(claims.get(ACCESS_KEY) != null && claims.get(ACCESS_KEY) instanceof List<?>) {
            ((List<Map<String,Object>>)claims.get(ACCESS_KEY)).stream()
                    .filter(map -> "mmweb".equals(map.get("appIdentifier")))
                    .forEach(m -> {
                        if(m.get("externalId") != null && m.get("accessScope") != null && m.get("accessType") != null) {
                            try {
                                AccessAuthority auth = new AccessAuthority(Long.parseLong(m.get("externalId").toString()),
                                        AccessScope.valueOf(m.get("accessScope").toString()),
                                        AccessType.valueOf(m.get("accessType").toString()));
                                authorities.add(auth);
                            } catch(Exception e) {
                                log.warn("Could not parse access authority from jwt:  " + m.toString());
                            }
                        }
                    });
        }

        User principal = new User(
                Optional.ofNullable(claims.getSubject()).orElseGet(
                        () -> Optional.ofNullable(claims.get("client_id", String.class)).orElseGet(
                                () -> (String)claims.getOrDefault("user_name", "unknown"))),
                "",
                authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String authToken) throws ParseException, JOSEException, BadJOSEException {
        JWT jwt = JWTParser.parse(authToken);

        log.trace("token algorithm: " + jwt.getHeader().getAlgorithm());
        if (JWSAlgorithm.HS512.equals(jwt.getHeader().getAlgorithm())) {
            log.warn("DEPRECATED old style authentication {}", JWSAlgorithm.HS512);
            SigningKeyResolver signingKeyResolver = new com.everag.mobilemanifest.bootstrap.application.security.SigningKeyResolver(oldSecretKey, getKeyFromAuthorizationServer);
            Jwts.parser().setSigningKeyResolver(signingKeyResolver).parseClaimsJws(authToken);
            return true;
        } else {
            ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = jwtProcessorMap.get(jwt.getHeader().getAlgorithm());
            JWTClaimsSet claims = jwtProcessor.process(jwt, null);
            return true;
        }
    }
}
