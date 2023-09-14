package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaCommonRepository<StudentsEntity, String> {
    // 自定义的查询和操作可以放在这里
}
