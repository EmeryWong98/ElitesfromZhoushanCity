package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.DegreeBindingEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DegreeBindingRepository extends JpaCommonRepository<DegreeBindingEntity, String> {
    List<DegreeBindingEntity> findByStudentId(String id);

    @Modifying
    @Query("delete from DegreeBindingEntity d where d.studentId = :studentId")
    void deleteByStudentId(@Param("studentId") String studentId);
}
