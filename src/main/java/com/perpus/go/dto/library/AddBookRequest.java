package com.perpus.go.dto.library;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
public class AddBookRequest {
    private String accessToken;
    private String coverImg;
    private String title;
    private Collection<String> authors;
    private Collection<String> genre;
    private int totalPages;
    private float rating;
    private int popularity;
    private String language;
    private String description;
    private boolean ebook;
    private boolean physicBook;
    private int loanDeadLine;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate publishedDate;
    private String publisher;
}
