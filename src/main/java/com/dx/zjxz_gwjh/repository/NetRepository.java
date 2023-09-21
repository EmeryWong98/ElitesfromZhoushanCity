package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.NetEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NetRepository extends JpaCommonRepository<NetEntity, String> {
    Optional<NetEntity> findByName(String netName);
}
