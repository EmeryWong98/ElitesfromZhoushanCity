package com.dx.zjxz_gwjh.repository;

import org.springframework.stereotype.Repository;

import com.dx.zjxz_gwjh.entity.DepartmentEntity;
import com.dx.easyspringweb.data.jpa.JpaCommonRepository;

@Repository
public interface DepartmentRepository extends JpaCommonRepository<DepartmentEntity, String> {

}
