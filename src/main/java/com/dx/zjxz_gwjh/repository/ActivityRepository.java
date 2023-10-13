package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.ActivityEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.enums.NetType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaCommonRepository<ActivityEntity, String> {

    /**
     * 根据网格员ID查询高校网格
     *
     * @param userId 网格员ID
     * @param netId  高校网格ID
     * @return 高校网格ID
     */
    @Query("SELECT h.id from HighSchoolNetEntity h " +
            "WHERE (:userId is null or h.userId = :userId) " +
            "AND (:netId is null or h.id = :netId) "
    )
    List<String> queryHighSchoolNetIdsByUserIdAndNetId(@Param("userId") String userId, @Param("netId") String netId);

    /**
     * 根据网格员ID查询区域网格
     *
     * @param userId 网格员ID
     * @param netId  区域网格ID
     * @return 区域网格ID
     */
    @Query("SELECT a.id from AreaNetEntity a " +
            "WHERE (:userId is null or a.userId = :userId) " +
            "AND (:netId is null or a.id = :netId) "
    )
    List<String> queryAreaNetIdsByUserIdAndNetId(@Param("userId") String userId, @Param("netId") String netId);

    /**
     * 根据网格信息查询所有学生
     *
     * @param netIds 网格ID
     * @return 学生列表
     */
    @Query("SELECT s FROM StudentsEntity s " +
            "WHERE s.areaNetId IN :netIds " +
            "OR s.highSchoolNetId IN :netIds")
    List<StudentsEntity> queryStudentByNetIds(@Param("netIds") List<String> netIds);

    /**
     * 根据当前时间查询正在进行的活动
     *
     * @param nowDate 当前时间
     * @return 活动列表
     */
    @Query("SELECT a FROM ActivityEntity a " +
            "WHERE a.netType = :netType " +
            "AND a.startTime <= :nowDate " +
            "AND a.endTime >= :nowDate")
    List<ActivityEntity> queryCurrActivityBy(@Param("netType") NetType netType, @Param("nowDate") Date nowDate);

    /**
     * 根据当前时间查询还未开始的活动
     *
     * @param nowDate 当前时间
     * @return 活动列表
     */
    @Query("SELECT a FROM ActivityEntity a " +
            "WHERE a.netType = :netType " +
            "AND a.startTime > :nowDate " +
            "AND a.endTime > :nowDate")
    List<ActivityEntity> queryWaitActivity(@Param("netType") NetType netType, @Param("nowDate") Date nowDate);
}
