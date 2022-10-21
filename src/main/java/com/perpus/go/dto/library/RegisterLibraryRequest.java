package com.perpus.go.dto.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterLibraryRequest {
    @NotNull(message = "name must not be null")
    private String name;
    @NotNull(message = "adress must not be null")
    private String address;
    @NotNull(message = "longitude must not be null")
    private Double longitude;
    @NotNull(message = "latitude must not be null")
    private Double latitude;
    private String wallpaperImg;
    private Collection<String> galleries;
}
