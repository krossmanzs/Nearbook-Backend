package com.perpus.go.dto.library;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class RegisterLibraryRequest {
    private String name;
    private String address;
    private String wallpaperImg;
    private Collection<String> galleries;
}
