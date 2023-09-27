package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.dto.HighSchoolNetOverviewDto;
import com.dx.zjxz_gwjh.dto.HighSchoolNetSimpleOverviewDto;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    int countByAcademicYearBetweenAndArea(int startYear, int endYear, String area);


    @Query("SELECT DISTINCT s.universityProvince FROM StudentsEntity s " +
            "WHERE s.area IN :areaNames " +
            "AND s.academicYear BETWEEN :startYear AND :endYear")
    List<String> findProvincesByAreaNamesAndYearRange(@Param("areaNames") List<String> areaNames,
                                                      @Param("startYear") int startYear,
                                                      @Param("endYear") int endYear);

    @Query("SELECT COUNT(DISTINCT s) FROM StudentsEntity s " +
            "WHERE s.universityProvince = :province " +
            "AND s.academicYear BETWEEN :startYear AND :endYear " +
            "AND s.area IN :selectedAreaNames")
    int countStudentsByProvinceAndYearRange(@Param("province") String province,
                                            @Param("startYear") int startYear,
                                            @Param("endYear") int endYear,
                                            @Param("selectedAreaNames") List<String> selectedAreaNames);

    @Query("SELECT COUNT(DISTINCT s.university) FROM StudentsEntity s WHERE s.university.province = :province AND s.area IN :areaNames AND s.academicYear BETWEEN :startYear AND :endYear")
    int countSchoolsByProvinceAndYearRange(@Param("province") String province, @Param("startYear") int startYear, @Param("endYear") int endYear, @Param("areaNames") List<String> areaNames);

    @Query("SELECT COUNT(s) FROM StudentsEntity s WHERE s.academicYear = :year AND s.area IN :areas")
    int countStudentsByYearAndAreas(@Param("year") int year, @Param("areas") List<String> areas);


    int countByAcademicYearBetweenAndAreaAndIsKeyContact(int startYear, int endYear, String name, boolean isKeyContact);

    int countByAcademicYearBetweenAndAreaAndDegreeAndIsKeyContact(int startYear, int endYear, String name, String degreeType, boolean isKeyContact);

    @Query("SELECT COUNT(DISTINCT s) FROM StudentsEntity s " +
            "WHERE s.universityProvince = :province " +
            "AND s.academicYear BETWEEN :startYear AND :endYear " +
            "AND s.area IN :selectedAreaNames " +
            "AND s.isKeyContact = :isKeyContact") // 添加重点学生条件
    int countKeyStudentsByProvinceAndYearRange(@Param("province") String province,
                                               @Param("startYear") int startYear,
                                               @Param("endYear") int endYear,
                                               @Param("selectedAreaNames") List<String> selectedAreaNames,
                                               @Param("isKeyContact") boolean isKeyContact); // 添加重点学生参数

    @Query("SELECT COUNT(DISTINCT s.university) FROM StudentsEntity s " +
            "WHERE s.university.province = :province " +
            "AND s.academicYear BETWEEN :startYear AND :endYear " +
            "AND s.area IN :areaNames " +
            "AND s.isKeyContact = :isKeyContact") // 添加重点学生条件
    int countKeySchoolsByProvinceAndYearRange(@Param("province") String province,
                                              @Param("startYear") int startYear,
                                              @Param("endYear") int endYear,
                                              @Param("areaNames") List<String> areaNames,
                                              @Param("isKeyContact") boolean isKeyContact); // 添加重点学生参数

    @Query("SELECT COUNT(s) FROM StudentsEntity s WHERE s.academicYear = :year AND s.area IN :areas AND s.isKeyContact = true") // 添加了重点学生筛选条件
    int countKeyStudentsByYearAndAreas(@Param("year") int year, @Param("areas") List<String> areas);

    @Query("SELECT new com.dx.zjxz_gwjh.dto.HighSchoolNetOverviewDto(" +
            "s.highSchoolName, s.highSchoolId, COUNT(DISTINCT s.highSchoolNetId), " +
            "COUNT(s), SUM(CASE WHEN s.isKeyContact = true THEN 1 ELSE 0 END)) " +
            "FROM StudentsEntity s " +
            "WHERE s.highSchoolName IS NOT NULL " +
            "GROUP BY s.highSchoolName, s.highSchoolId")

    List<HighSchoolNetOverviewDto> findHighSchoolNetOverview();

    @Query("SELECT new com.dx.zjxz_gwjh.dto.HighSchoolNetSimpleOverviewDto(" +
            "s.highSchoolId, s.highSchoolName, COUNT(DISTINCT s.highSchoolNetId), COUNT(s)) " +
            "FROM StudentsEntity s " +
            "GROUP BY s.highSchoolId, s.highSchoolName " +
            "ORDER BY COUNT(s) DESC")
    List<HighSchoolNetSimpleOverviewDto> findHighSchoolNetSimpleOverview();

    @Query("SELECT s.highSchoolNetId, s.highSchoolNetContactor, s.id, s.name " +
            "FROM StudentsEntity s " +
            "WHERE s.highSchoolId = :highSchoolId " +
            "AND s.highSchoolNetId IS NOT NULL " +
            "AND (:netId IS NULL OR s.highSchoolNetId = :netId) " +
            "AND (:graduationYear IS NULL OR s.academicYear = :graduationYear) " +
            "ORDER BY s.highSchoolNetContactor, s.id")
    List<Object[]> findTeachersAndStudents(@Param("highSchoolId") String highSchoolId, @Param("graduationYear") Integer graduationYear, @Param("netId") String netId);

    int countByUniversityId(String id);

    int countByUniversityIdAndIsSupremeAndIsKeyContact(String id, boolean b, boolean b1);

    int countByUniversityIdAndAcademicYearBetween(String id, Integer startYear, Integer endYear);

    int countByUniversityIdAndMajorAndIsSupremeAndIsKeyContactAndAcademicYearBetween(String id, String major, boolean b, boolean b1, Integer startYear, Integer endYear);


    // 自定义的查询和操作可以放在这里
}
