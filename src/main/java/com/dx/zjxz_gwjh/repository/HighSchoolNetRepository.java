package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.dto.NetActivityDto;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HighSchoolNetRepository extends JpaCommonRepository<HighSchoolNetEntity, String> {

    HighSchoolNetEntity findByName(String highSchoolNetName);

    HighSchoolNetEntity findByNameAndUserNameAndPhoneNumberAndAreaCodeAndLocation(String highSchoolNetName, String highSchoolNetContactor, String highSchoolNetContactorMobile, String highSchoolNetAreaCode, String highSchoolNetLocation);

    @Query("SELECT new com.dx.zjxz_gwjh.dto.NetActivityDto(" +
            "n.id, n.name, n.userName, n.score) " +
            "FROM HighSchoolNetEntity n " +
            "ORDER BY n.score DESC")
    List<NetActivityDto> findHighSchoolNetActivityRanking();

    List<HighSchoolNetEntity> findByAreaCode(String areaCode);
}
