package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.ZLBStudentsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ZLBStudentsRepository extends JpaCommonRepository<ZLBStudentsEntity, String> {
    ZLBStudentsEntity getByIdCard(String idCard);
}
