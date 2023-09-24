package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.AreaCodeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaCodeRepository extends JpaCommonRepository<AreaCodeEntity, String> {
    Optional<AreaCodeEntity> findByName(String name);

    @Query("SELECT a.name FROM AreaCodeEntity a WHERE a.id IN :ids")
    List<String> findNamesById(@Param("ids") List<String> ids);

    @Query("SELECT a.name FROM AreaCodeEntity a WHERE a.id IN :areaId")
    String findNameById(String areaId);
}
