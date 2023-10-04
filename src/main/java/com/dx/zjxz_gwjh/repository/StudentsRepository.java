package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.dto.HighSchoolNetOverviewDto;
import com.dx.zjxz_gwjh.dto.HighSchoolNetSimpleOverviewDto;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.enums.DegreeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface StudentsRepository extends JpaCommonRepository<StudentsEntity, String> {
//    List<StudentsEntity> findByAcademicYearBetweenAndArea(int startYear, int endYear, String area);
//
//    @Query("SELECT DISTINCT area FROM StudentsEntity")
//    List<String> findAllDistinctAreas();
//
//    @Query("SELECT s.academicYear, COUNT(s) FROM StudentsEntity s WHERE s.area = ?1 AND s.isKeyContact = true GROUP BY s.academicYear")
//    List<Object[]> countKeyContactsByAreaAndAcademicYear(String area);
//
//    List<StudentsEntity> findByIsKeyContactTrue();
//
//    Collection<Object> findByAcademicYearBetweenAndAreaAndIsKeyContactTrue(int startYear, int endYear, String area);

    StudentsEntity findByIdCard(String idCard);

    int countByAcademicYearBetweenAndArea(int startYear, int endYear, String area);


    @Query("SELECT DISTINCT u.province " +
            "FROM UniversityEntity u " +
            "JOIN DegreeBindingEntity d ON u.id = d.universityId " +
            "JOIN StudentsEntity s ON d.studentId = s.id " +
            "WHERE s.area IN :areaNames " +
            "AND s.academicYear BETWEEN :startYear AND :endYear " +
            "AND (CASE WHEN d.degree = 'Phd' THEN 2 " +
            "WHEN d.degree = 'Graduate' THEN 1 " +
            "ELSE 0 END) = " +
            "(SELECT MAX(CASE WHEN degree = 'Phd' THEN 2 " +
            "WHEN degree = 'Graduate' THEN 1 " +
            "ELSE 0 END) FROM DegreeBindingEntity WHERE studentId = s.id)")
    List<String> findProvincesByAreaNamesAndYearRange(@Param("areaNames") List<String> areaNames,
                                                      @Param("startYear") int startYear,
                                                      @Param("endYear") int endYear);




    @Query("SELECT COUNT(DISTINCT s) FROM StudentsEntity s " +
            "JOIN DegreeBindingEntity d ON s.id = d.studentId " +
            "JOIN UniversityEntity u ON d.universityId = u.id " +
            "WHERE u.province = :province " +
            "AND s.academicYear BETWEEN :startYear AND :endYear " +
            "AND s.area IN :selectedAreaNames " +
            "AND (CASE WHEN d.degree = 'phd' THEN 2 " +
            "WHEN d.degree = 'graduate' THEN 1 " +
            "ELSE 0 END) = " +
            "(SELECT MAX(CASE WHEN degree = 'phd' THEN 2 " +
            "WHEN degree = 'graduate' THEN 1 " +
            "ELSE 0 END) FROM DegreeBindingEntity where studentId = s.id)")
    int countStudentsByProvinceAndYearRange(@Param("province") String province,
                                            @Param("startYear") int startYear,
                                            @Param("endYear") int endYear,
                                            @Param("selectedAreaNames") List<String> selectedAreaNames);




    @Query("SELECT COUNT(DISTINCT u) FROM StudentsEntity s " +
            "JOIN DegreeBindingEntity d ON s.id = d.studentId " +
            "JOIN UniversityEntity u ON d.universityId = u.id " +
            "WHERE u.province = :province " +
            "AND s.area IN :areaNames " +
            "AND s.academicYear BETWEEN :startYear AND :endYear " +
            "AND (CASE WHEN d.degree = 'phd' THEN 2 " +
            "WHEN d.degree = 'graduate' THEN 1 " +
            "ELSE 0 END) = " +
            "(SELECT MAX(CASE WHEN degree = 'phd' THEN 2 " +
            "WHEN degree = 'graduate' THEN 1 " +
            "ELSE 0 END) FROM DegreeBindingEntity where studentId = s.id)")
    int countSchoolsByProvinceAndYearRange(@Param("province") String province,
                                           @Param("startYear") int startYear,
                                           @Param("endYear") int endYear,
                                           @Param("areaNames") List<String> areaNames);




    @Query("SELECT COUNT(s) FROM StudentsEntity s WHERE s.academicYear = :year AND s.area IN :areas")
    int countStudentsByYearAndAreas(@Param("year") int year, @Param("areas") List<String> areas);


    int countByAcademicYearBetweenAndAreaAndIsKeyContact(int startYear, int endYear, String name, boolean isKeyContact);

    @Query("SELECT COUNT(DISTINCT s) FROM StudentsEntity s " +
            "JOIN DegreeBindingEntity d ON s.id = d.studentId " +
            "WHERE s.academicYear BETWEEN :startYear AND :endYear " +
            "AND s.area = :area " +
            "AND s.isKeyContact = :isKeyContact " +
            "AND d.degree = :degreeT " +
            "AND (CASE " +
            "  WHEN d.degree = 'PhD' THEN 3 " +
            "  WHEN d.degree = 'Graduate' THEN 2 " +
            "  WHEN d.degree = 'Undergraduate' THEN 1 " +
            "  ELSE 0 END) = " +
            "(SELECT MAX(CASE " +
            "  WHEN degree = 'PhD' THEN 3 " +
            "  WHEN degree = 'Graduate' THEN 2 " +
            "  WHEN degree = 'Undergraduate' THEN 1 " +
            "  ELSE 0 END) " +
            " FROM DegreeBindingEntity " +
            " WHERE studentId = s.id)")
    int countByAcademicYearBetweenAndAreaAndDegreeAndIsKeyContact(@Param("startYear") int startYear,
                                                                  @Param("endYear") int endYear,
                                                                  @Param("area") String area,
                                                                  @Param("degreeT") DegreeType degreeT,
                                                                  @Param("isKeyContact") boolean isKeyContact);



    @Query("SELECT COUNT(DISTINCT u) FROM StudentsEntity s " +
            "JOIN DegreeBindingEntity d ON s.id = d.studentId " +
            "JOIN UniversityEntity u ON d.universityId = u.id " +
            "WHERE u.province = :province " +
            "AND s.area IN :areaNames " +
            "AND s.academicYear BETWEEN :startYear AND :endYear " +
            "AND (CASE " +
            "WHEN d.degree = 'PhD' THEN 3 " + // 为PhD设置不同的值
            "WHEN d.degree = 'graduate' THEN 2 " +
            "WHEN d.degree = 'undergraduate' THEN 1 " + // 包含本科学位
            "ELSE 0 END) = " +
            "(SELECT MAX(CASE " +
            "WHEN degree = 'PhD' THEN 3 " + // 为PhD设置不同的值
            "WHEN degree = 'graduate' THEN 2 " +
            "WHEN degree = 'undergraduate' THEN 1 " + // 包含本科学位
            "ELSE 0 END) FROM DegreeBindingEntity WHERE studentId = s.id) " +
            "AND s.isKeyContact = :isKeyContact ") // 添加重点学生条件
    int countKeyStudentsByProvinceAndYearRange(@Param("province") String province,
                                           @Param("startYear") int startYear,
                                           @Param("endYear") int endYear,
                                           @Param("areaNames") List<String> areaNames,
                                           @Param("isKeyContact") boolean isKeyContact); // 添加重点学生参数


    @Query("SELECT COUNT(DISTINCT u) FROM StudentsEntity s " +
            "JOIN DegreeBindingEntity d ON s.id = d.studentId " +
            "JOIN UniversityEntity u ON d.universityId = u.id " +
            "WHERE u.province = :province " +
            "AND s.academicYear BETWEEN :startYear AND :endYear " +
            "AND s.area IN :areaNames " +
            "AND s.isKeyContact = :isKeyContact " + // 考虑重点学生条件
            "AND (CASE " +
            "WHEN d.degree = 'PhD' THEN 3 " +
            "WHEN d.degree = 'Graduate' THEN 2 " +
            "WHEN d.degree = 'Undergraduate' THEN 1 " +
            "ELSE 0 END) = " +
            "(SELECT MAX(CASE " +
            "WHEN degree = 'PhD' THEN 3 " +
            "WHEN degree = 'Graduate' THEN 2 " +
            "WHEN degree = 'Undergraduate' THEN 1 " +
            "ELSE 0 END) FROM DegreeBindingEntity WHERE studentId = s.id)")
    int countKeySchoolsByProvinceAndYearRange(@Param("province") String province,
                                              @Param("startYear") int startYear,
                                              @Param("endYear") int endYear,
                                              @Param("areaNames") List<String> areaNames,
                                              @Param("isKeyContact") boolean isKeyContact); // 使用重点学生参数

    @Query("SELECT COUNT(s) FROM StudentsEntity s WHERE s.academicYear = :year AND s.area IN :areas AND s.isKeyContact = true") // 添加了重点学生筛选条件
    int countKeyStudentsByYearAndAreas(@Param("year") int year, @Param("areas") List<String> areas);

    @Query("SELECT new com.dx.zjxz_gwjh.dto.HighSchoolNetOverviewDto(" +
            "hs.name, hs.id, ac.id, COUNT(DISTINCT s.highSchoolNetId), " +
            "COUNT(s), SUM(CASE WHEN s.isKeyContact = true THEN 1 ELSE 0 END), hs.lon, hs.lat) " +
            "FROM StudentsEntity s " +
            "JOIN HighSchoolEntity hs ON s.highSchoolId = hs.id " +
            "JOIN HighSchoolNetEntity hsn ON hs.name = hsn.location " +
            "JOIN AreaCodeEntity ac ON hsn.areaCode = ac.code " +
            "WHERE hs.name IS NOT NULL " +
            "GROUP BY hs.name, hs.id, ac.id, hs.lon, hs.lat")
    List<HighSchoolNetOverviewDto> findHighSchoolNetOverview();



    @Query("SELECT new com.dx.zjxz_gwjh.dto.HighSchoolNetSimpleOverviewDto(" +
            "hs.id, hs.name, COUNT(DISTINCT s.highSchoolNetId), COUNT(s)) " +
            "FROM StudentsEntity s " +
            "JOIN HighSchoolEntity hs ON s.highSchoolId = hs.id " +
            "GROUP BY hs.highSchoolId, hs.name " +
            "ORDER BY COUNT(s) DESC")
    List<HighSchoolNetSimpleOverviewDto> findHighSchoolNetSimpleOverview();


    @Query("SELECT s.highSchoolNetId, hsn.userName, s.id, s.name " +
            "FROM StudentsEntity s " +
            "JOIN HighSchoolNetEntity hsn ON s.highSchoolNetId = hsn.id " +
            "JOIN HighSchoolEntity hs ON s.highSchoolId = hs.id " +
            "WHERE hs.highSchoolId = :highSchoolId " +
            "AND s.highSchoolNetId IS NOT NULL " +
            "AND (:netId IS NULL OR s.highSchoolNetId = :netId) " +
            "AND (:graduationYear IS NULL OR s.academicYear = :graduationYear) " +
            "ORDER BY hsn.userName, s.id")
    List<Object[]> findTeachersAndStudents(@Param("highSchoolId") String highSchoolId,
                                           @Param("graduationYear") Integer graduationYear,
                                           @Param("netId") String netId);



//    int countByUniversityId(String id);
//
//    int countByUniversityIdAndIsSupremeAndIsKeyContact(String id, boolean b, boolean b1);

    @Query("SELECT COUNT(DISTINCT s) FROM StudentsEntity s " +
            "JOIN DegreeBindingEntity d ON s.id = d.studentId " +
            "WHERE d.universityId = :universityId " +
            "AND s.academicYear BETWEEN :startYear AND :endYear")
    int countByUniversityIdAndAcademicYearBetween(@Param("universityId") String universityId,
                                                  @Param("startYear") Integer startYear,
                                                  @Param("endYear") Integer endYear);
    @Query("SELECT COUNT(DISTINCT s) FROM StudentsEntity s " +
            "JOIN DegreeBindingEntity d ON s.id = d.studentId " +
            "WHERE d.universityId = :universityId " +
            "AND d.major = :major " +
            "AND s.isSupreme = :isSupreme " +
            "AND s.isKeyContact = :isKeyContact " +
            "AND s.academicYear BETWEEN :startYear AND :endYear")
    int countByUniversityIdAndMajorAndIsSupremeAndIsKeyContactAndAcademicYearBetween(@Param("universityId") String universityId,
                                                                                     @Param("major") String major,
                                                                                     @Param("isSupreme") boolean isSupreme,
                                                                                     @Param("isKeyContact") boolean isKeyContact,
                                                                                     @Param("startYear") Integer startYear,
                                                                                     @Param("endYear") Integer endYear);

    List<StudentsEntity> findByUnionNetId(String id);


//    @Query("SELECT s FROM StudentsEntity s")
//    Stream<StudentsEntity> streamAllStudents();


    // 自定义的查询和操作可以放在这里
}
