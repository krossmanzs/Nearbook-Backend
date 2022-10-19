package com.perpus.go.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterKtpUserRequest {
    private String nik;
    private String namaLengkap;
    private String tempatLahir;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate tanggalLahir;
    private String alamat;
    private String rt;
    private String rw;
    private String kecamatan;
    private String agama;
    private int kodeKawin;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate berlaku;
}
