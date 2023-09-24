package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.OfficerNetEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficerNetRepository extends JpaCommonRepository<OfficerNetEntity, String> {
    OfficerNetEntity findByUserName(String officerNetName);

    OfficerNetEntity findByUserNameAndTitle(String officerNetName, String officerNetPosition);
}
