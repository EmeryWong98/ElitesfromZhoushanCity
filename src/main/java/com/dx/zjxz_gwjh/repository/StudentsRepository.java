package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentsRepository extends JpaCommonRepository<StudentsEntity, String> {
    List<StudentsEntity> findByAcademicYearBetweenAndArea(int startYear, int endYear, String area);

    @Query("SELECT DISTINCT area FROM StudentsEntity")
    List<String> findAllDistinctAreas();

    @Query("SELECT s.academicYear, COUNT(s) FROM StudentsEntity s WHERE s.area = ?1 AND s.isKeyContact = true GROUP BY s.academicYear")
    List<Object[]> countKeyContactsByAreaAndAcademicYear(String area);

    List<StudentsEntity> findByIsKeyContactTrue();

    Collection<Object> findByAcademicYearBetweenAndAreaAndIsKeyContactTrue(int startYear, int endYear, String area);

    StudentsEntity findByIdCard(String idCard);

    // 自定义的查询和操作可以放在这里
}
