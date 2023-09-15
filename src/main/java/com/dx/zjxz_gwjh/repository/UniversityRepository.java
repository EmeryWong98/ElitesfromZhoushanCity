package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaCommonRepository<UniversityEntity, String> {
    UniversityEntity findByName(String universityName);
    // 自定义的查询和操作可以放在这里
}
