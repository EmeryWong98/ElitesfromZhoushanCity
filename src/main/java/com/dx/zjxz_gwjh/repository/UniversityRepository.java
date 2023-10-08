package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
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

    List<UniversityEntity> findByIsKeyMajorAndProvince(boolean b, String province);

    List<UniversityEntity> findByIsSupremeAndProvinceOrderByXorder(boolean b, String province);

    @Query("SELECT DISTINCT u.province FROM UniversityEntity u")
    List<String> findDistinctProvince();

    @Query("SELECT COUNT(u) FROM UniversityEntity u WHERE (u.isKeyMajor = true OR u.isSupreme = true) AND u.province = :province")
    int countKeyUniversitiesByProvince(@Param("province") String province);


    int countByProvince(@Param("province") String province);

}
