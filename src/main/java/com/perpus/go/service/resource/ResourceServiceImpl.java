package com.perpus.go.service.resource;

import com.perpus.go.model.user.ktp.Agama;
import com.perpus.go.model.user.ktp.Kawin;
import com.perpus.go.repository.user.AgamaRepository;
import com.perpus.go.repository.user.KawinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService{
    private final KawinRepository kawinRepository;
    private final AgamaRepository agamaRepository;

    @Override
    public List<Kawin> getKawins() {
        return kawinRepository.findAll();
    }

    @Override
    public List<Agama> getAgamas() {
        return agamaRepository.findAll();
    }
}
