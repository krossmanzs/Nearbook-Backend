package com.perpus.go.model.ktp;


import com.perpus.go.dto.RegisterKtpUserRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ktp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nik;
    private String namaLengkap;
    private String tempatLahir;
    private LocalDate tanggalLahir;
    private String alamat;
    private String rt;
    private String rw;
    private String kecamatan;

    @ManyToOne(targetEntity = Agama.class)
    private Agama agama;

    @ManyToOne(targetEntity = Kawin.class)
    private Kawin kawin;

    private LocalDate berlaku;

    public Ktp(RegisterKtpUserRequest userKtpRequest) {
        this.nik = userKtpRequest.getNik();
        this.namaLengkap = userKtpRequest.getNamaLengkap();
        this.tempatLahir = userKtpRequest.getTempatLahir();
        this.tanggalLahir = userKtpRequest.getTanggalLahir();
        this.alamat = userKtpRequest.getAlamat();
        this.rt = userKtpRequest.getRt();
        this.rw = userKtpRequest.getRw();
        this.kecamatan = userKtpRequest.getKecamatan();
        this.berlaku = userKtpRequest.getBerlaku();
    }
}
