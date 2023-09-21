package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.AreaCodeEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaCodeRepository extends JpaCommonRepository<AreaCodeEntity, String> {
    Optional<AreaCodeEntity> findByName(String name);
}
