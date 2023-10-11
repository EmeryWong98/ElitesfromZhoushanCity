package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.StudentJourneyLogEntity;
import com.dx.zjxz_gwjh.enums.DegreeType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StudentJourneyLogRepository extends JpaCommonRepository<StudentJourneyLogEntity, String> {
    @Query("SELECT s FROM StudentJourneyLogEntity s WHERE s.studentName = :studentName AND s.logTime BETWEEN :startTime AND :endTime")
    List<StudentJourneyLogEntity> findByStudentNameAndTimeRange(@Param("studentName") String studentName,
                                                                @Param("startTime") Date startTime,
                                                                @Param("endTime") Date endTime);


    @Query("SELECT COUNT(s) FROM StudentJourneyLogEntity s WHERE s.logTime BETWEEN :startTime AND :endTime")
    int countByTimeRange(@Param("startTime") Date startTime,
                         @Param("endTime") Date endTime);

}
