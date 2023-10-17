package com.dx.zjxz_gwjh.repository;


import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HighSchoolRepository extends JpaCommonRepository<HighSchoolEntity, String> {
    HighSchoolEntity findByName(String highSchoolName);

    List<HighSchoolEntity> findByIsShowTrueOrderByXorder();
}
