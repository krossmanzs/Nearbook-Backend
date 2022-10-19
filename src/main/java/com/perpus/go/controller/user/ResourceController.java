package com.perpus.go.controller.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perpus.go.config.Token;
import com.perpus.go.model.user.User;
import com.perpus.go.model.user.ktp.Agama;
import com.perpus.go.model.user.ktp.Kawin;
import com.perpus.go.service.resource.ResourceService;
import com.perpus.go.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ResourceController {
    private final UserService userService;
    private final ResourceService resourceService;

    @GetMapping("/option/kawin")
    public ResponseEntity<List<Kawin>> getKawinOption(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken
    ) {
        return new ResponseEntity<>(resourceService.getKawins(), HttpStatus.OK);
    }

    @GetMapping("/option/agama")
    public ResponseEntity<List<Agama>> getAgamaOption(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken
    ) {
        return new ResponseEntity<>(resourceService.getAgamas(), HttpStatus.OK);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(Token.SECRET_KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                Optional<User> user = userService.findUserByEmail(email);
                if (user.isEmpty()) {
                    throw new UsernameNotFoundException("No username found");
                }
                String access_token = JWT.create()
                        .withSubject(user.get().getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + Token.ACCESS_EXP_TIME))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", new ArrayList<>())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> error = new HashMap<>();
                error.put("error: ", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            response.setHeader("error", "Refresh token is missing");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String,String> error = new HashMap<>();
            error.put("error_msg", "Refresh token is missing");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }
}
