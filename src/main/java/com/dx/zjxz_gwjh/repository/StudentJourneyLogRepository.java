package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.StudentJourneyLogEntity;
import com.dx.zjxz_gwjh.enums.DegreeType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface StudentJourneyLogRepository extends JpaCommonRepository<StudentJourneyLogEntity, String> {
    /**
     * 查询已回舟学子的回舟记录
     *
     * @param studentId 学生id
     * @return 回舟记录
     */
    @Query("SELECT s FROM StudentJourneyLogEntity s " +
            "WHERE s.studentId = :studentId")
    PagingData<StudentJourneyLogEntity> findByStudentNameAndTimeRange(@Param("studentId") String studentId);

    /**
     * 搜索已回舟的学子总数
     *
     * @param startTime 学年开始时间
     * @param endTime   学年结束时间
     * @return 已回舟的学子总数
     */
    @Query("SELECT COUNT(s) FROM StudentsEntity s " +
            "WHERE s.academicYear BETWEEN :startTime AND :endTime " +
            "AND s.isBack = true")
    int CountByTimeRange(@Param("startTime") int startTime,
                         @Param("endTime") int endTime);

    /**
     * 根据学历搜索已回舟的学子数量
     *
     * @param startTime  学年开始时间
     * @param endTime    学年结束时间
     * @param degreeType 学历类型
     * @return 已回舟的学子数量
     */
    @Query("SELECT COUNT(s) FROM StudentsEntity s " +
            "JOIN DegreeBindingEntity d ON s.id = d.studentId " +
            "WHERE s.academicYear BETWEEN :startTime AND :endTime " +
            "AND s.isBack = true " +
            "AND d.degree = :degreeType")
    int CountByTimeRangeAndDegreeAndIsBack(@Param("startTime") int startTime,
                                           @Param("endTime") int endTime,
                                           @Param("degreeType") DegreeType degreeType);

    /**
     * 根据地区合并已回舟的学子数量
     *
     * @param startTime 学年开始时间
     * @param endTime   学年结束时间
     * @return 已回舟的学子数量
     */
    @Query("SELECT s.area, COUNT(s) FROM StudentsEntity s " +
            "WHERE s.academicYear BETWEEN :startTime AND :endTime " +
            "AND s.isBack = true " +
            "GROUP BY s.area")
    List<Object[]> countByTimeRangeAndAreaAndIsBack(@Param("startTime") int startTime,
                                                    @Param("endTime") int endTime);

    /**
     * 根据届次合并已回舟的学子数量
     *
     * @param startTime 学年开始时间
     * @param endTime   学年结束时间
     * @return 已回舟的学子数量
     */
    @Query("SELECT s.academicYear, COUNT(s) FROM StudentsEntity s " +
            "WHERE s.academicYear BETWEEN :startTime AND :endTime " +
            "AND s.isBack = true " +
            "GROUP BY s.academicYear " +
            "ORDER BY s.academicYear DESC ")
    List<Object[]> countByTimeRangeAndAcademicYearAndIsBack(@Param("startTime") int startTime,
                                                            @Param("endTime") int endTime);
}
