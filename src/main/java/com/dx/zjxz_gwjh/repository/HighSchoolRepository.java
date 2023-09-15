package com.dx.zjxz_gwjh.repository;


import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface HighSchoolRepository extends JpaCommonRepository<HighSchoolEntity, String> {
    HighSchoolEntity findByName(String highSchoolName);
    // 自定义的查询和操作可以放在这里
}
