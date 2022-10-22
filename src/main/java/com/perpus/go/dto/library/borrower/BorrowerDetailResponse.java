package com.perpus.go.dto.library.borrower;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerDetailResponse {
    private Long id;
    private String borrowerName;
    private String bookName;
    private String qrCode;
    private LocalDate createdAt;
    private LocalDate borrowedAt;
    private LocalDate returnedAt;
}
