package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.dto.HighSchoolNetActivityDto;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HighSchoolNetRepository extends JpaCommonRepository<HighSchoolNetEntity, String> {
    HighSchoolNetEntity findByName(String highSchoolNetName);

    HighSchoolNetEntity findByNameAndUserNameAndPhoneNumberAndAreaCodeAndLocation(String highSchoolNetName, String highSchoolNetContactor, String highSchoolNetContactorMobile, String highSchoolNetAreaCode, String highSchoolNetLocation);

    @Query("SELECT new com.dx.zjxz_gwjh.dto.HighSchoolNetActivityDto(" +
            "n.id, n.name, n.userName, n.score) " +
            "FROM HighSchoolNetEntity n " +
            "ORDER BY n.score DESC")
    List<HighSchoolNetActivityDto> findHighSchoolNetActivityRanking();
}
