package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.dto.AreaNetOverviewDto;
import com.dx.zjxz_gwjh.dto.NetActivityDto;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaNetRepository extends JpaCommonRepository<AreaNetEntity, String> {
    AreaNetEntity findByName(String name);

    AreaNetEntity findByNameAndUserNameAndPhoneNumberAndAreaCodeAndLocation(String areaNetName, String areaNetContactor, String areaNetContactorMobile, String areaNetAreaCode, String areaNetLocation);

    @Query("SELECT new com.dx.zjxz_gwjh.dto.NetActivityDto(" +
            "n.id, n.name, n.userName, n.score) " +
            "FROM AreaNetEntity n " +
            "ORDER BY n.score DESC")
    List<NetActivityDto> findAreaNetActivityRanking();

    @Query("SELECT new com.dx.zjxz_gwjh.dto.AreaNetOverviewDto(" +
            "ac.name, ac.id, COUNT(DISTINCT s.areaNetId), " +
            "COUNT(s), SUM(CASE WHEN s.isKeyContact = true THEN 1 ELSE 0 END)) " +
            "FROM StudentsEntity s " +
            "JOIN AreaCodeEntity ac ON s.area = ac.name " +
            "WHERE ac.name IS NOT NULL " +
            "GROUP BY ac.name, ac.id")
    List<AreaNetOverviewDto> findAreaNetOverview();

    @Query("SELECT s.areaNetId, an.userName, s.id, s.name, s.sex " +
            "FROM StudentsEntity s " +
            "JOIN AreaNetEntity an ON s.areaNetId = an.id " +
            "JOIN AreaCodeEntity ac ON s.area = ac.name " +
            "WHERE ac.id = :areaId " +
            "AND s.areaNetId IS NOT NULL " +
            "AND (:netId IS NULL OR s.areaNetId = :netId) " +
            "AND (:graduationYear IS NULL OR s.academicYear = :graduationYear) " +
            "ORDER BY an.userName, s.id")
    List<Object[]> findTeachersAndStudents(@Param("areaId") String areaId,
                                           @Param("graduationYear") Integer graduationYear,
                                           @Param("netId") String netId);

    List<AreaNetEntity> findByAreaCodeOrderByNameDesc(String areaCode);

    @Query("SELECT a FROM AreaNetEntity a WHERE a.userId = :userId")
    List<AreaNetEntity> findByUserId(String userId);

    boolean existsByUserId(String userId);
}
