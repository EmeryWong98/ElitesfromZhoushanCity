package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
//
@Repository
public interface UniversityRepository extends JpaCommonRepository<UniversityEntity, String> {

    UniversityEntity findByName(String trim);
//
//    List<UniversityEntity> findByProvince(String province);
//
//    UniversityEntity findByName(String name);

    List<UniversityEntity> findByIsSupremeAndProvince(boolean b, String province);

    List<UniversityEntity> findByIsKeyMajorAndProvince(boolean b, String province);
}
