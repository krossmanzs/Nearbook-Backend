package com.perpus.go.service.resource;

import com.perpus.go.model.user.ktp.Agama;
import com.perpus.go.model.user.ktp.Kawin;

import java.util.List;

public interface ResourceService {
    List<Kawin> getKawins();
    List<Agama> getAgamas();
}
