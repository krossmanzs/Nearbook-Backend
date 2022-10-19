package com.perpus.go.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perpus.go.config.Token;
import com.perpus.go.exception.BadRequestException;
import com.perpus.go.exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;

@Slf4j
public class Util {
    // src: https://www.baeldung.com/java-email-validation-regex
    public static boolean patternNotMatches(String str, String regexPattern) {
        return !Pattern.compile(regexPattern)
                .matcher(str)
                .matches();
    }

    public static String generateVerificationCode() {
        return String.format("%06d", (int)Math.floor(Math.random()*(999999 + 1)+0));
    }

    public static void saveFile(
            String uploadDir,
            String fileName,
            MultipartFile multipartFile
    ) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save image file: " + fileName, e);
        }
    }

    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(index + 1);
        } else {
            return fileName;
        }
    }

    public static String generatedHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static String getEmailFromAccessToken(String accessToken) {
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            try {
                String token = accessToken.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(Token.SECRET_KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                return decodedJWT.getSubject();
            } catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
                throw new ForbiddenException(e.getMessage());
            }
        } else {
            throw new BadRequestException("Invalid token format");
        }
    }
}
