package com.perpus.go.dto.library;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequest {
    private String coverImg;
    private String title;
    private Collection<String> authors;
    private Collection<String> genre;
    private int totalPages;
    private String language;
    private String description;
    private boolean ebook;
    private boolean physicBook;
    private int loanDeadLine;
    @JsonSerialize(as = LocalDate.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate publishedDate;
    private String publisher;
}
