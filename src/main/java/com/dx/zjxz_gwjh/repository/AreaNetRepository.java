package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaNetRepository extends JpaCommonRepository<AreaNetEntity, String> {
    AreaNetEntity findByName(String name);

    AreaNetEntity findByNameAndUserNameAndPhoneNumberAndAreaCodeAndLocation(String areaNetName, String areaNetContactor, String areaNetContactorMobile, String areaNetAreaCode, String areaNetLocation);
}
