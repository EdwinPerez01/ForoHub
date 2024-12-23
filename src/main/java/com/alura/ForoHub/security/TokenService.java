package com.alura.ForoHub.security;

import com.alura.ForoHub.usuarios.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foro_hub")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaDeExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("El token es nulo o vacío");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // Validando firma
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("foro_hub")
                    .build()
                    .verify(token);
            String subject = verifier.getSubject();
            if (subject == null) {
                throw new UsernameNotFoundException("El token no tiene un sujeto válido");
            }
            return subject;
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error al verificar el token JWT", exception);
        }
    }

    private Instant generarFechaDeExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
