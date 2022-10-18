package com.perpus.go.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

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
}
