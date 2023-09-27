package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaCommonRepository<UniversityEntity, String> {
    UniversityEntity findByNameAndProvince(String name, String province);

    List<UniversityEntity> findByProvince(String province);

    UniversityEntity findByName(String name);


    // 自定义的查询和操作可以放在这里
}
