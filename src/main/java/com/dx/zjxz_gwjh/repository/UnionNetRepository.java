package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.UnionNetEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UnionNetRepository extends JpaCommonRepository<UnionNetEntity, String> {
    UnionNetEntity findByName(String unionNetName);

    UnionNetEntity findByNameAndUserNameAndPhoneNumberAndLocation(String unionNetName, String unionNetContactor, String unionNetContactorMobile, String unionNetLocation);
}
