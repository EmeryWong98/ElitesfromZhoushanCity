package com.dx.zjxz_gwjh.service;

import com.dx.zjxz_gwjh.repository.AreaCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dx.zjxz_gwjh.entity.AreaCodeEntity;

@Service
public class AreaCodeService {
    @Autowired
    private AreaCodeRepository areaCodeRepository;

    public String getAreaCodeByName(String name) {
        return areaCodeRepository.findByName(name)
                .map(AreaCodeEntity::getCode)
                .orElse(null);
    }
}
