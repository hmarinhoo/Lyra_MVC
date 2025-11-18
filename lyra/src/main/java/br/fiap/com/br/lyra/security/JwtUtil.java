package br.fiap.com.br.lyra.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public class JwtUtil {

    private final String secret;
    private final long validitySeconds;

    public JwtUtil(String secret, long validitySeconds) {
        this.secret = secret;
        this.validitySeconds = validitySeconds;
    }

    public String generateToken(String username) {
        long exp = Instant.now().getEpochSecond() + validitySeconds;
        String payload = username + ":" + exp;
        String sig = hmacSha256(payload, secret);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString((payload + ":" + sig).getBytes(StandardCharsets.UTF_8));
        return token;
    }

    public boolean validateToken(String token) {
        try {
            String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = decoded.split(":");
            if (parts.length < 3) return false;
            String username = parts[0];
            long exp = Long.parseLong(parts[1]);
            String sig = parts[2];
            String payload = username + ":" + exp;
            String expected = hmacSha256(payload, secret);
            if (!expected.equals(sig)) return false;
            return Instant.now().getEpochSecond() < exp;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        try {
            String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = decoded.split(":");
            if (parts.length < 3) return null;
            return parts[0];
        } catch (Exception e) {
            return null;
        }
    }

    private static String hmacSha256(String data, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC", e);
        }
    }
}
