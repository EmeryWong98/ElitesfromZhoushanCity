package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.dto.NetActivityDto;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.UnionNetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnionNetRepository extends JpaCommonRepository<UnionNetEntity, String> {
    UnionNetEntity findByName(String unionNetName);

    UnionNetEntity findByNameAndUserNameAndPhoneNumberAndLocation(String unionNetName, String unionNetContactor, String unionNetContactorMobile, String unionNetLocation);

    @Query("SELECT new com.dx.zjxz_gwjh.dto.NetActivityDto(" +
            "n.id, n.name, n.userName, n.score) " +
            "FROM UnionNetEntity n " +
            "ORDER BY n.score DESC")
    List<NetActivityDto> getUnionNetActivityRanking();


    UnionNetEntity findByUserId(String userId);

    boolean existsByUserId(String userId);
}
