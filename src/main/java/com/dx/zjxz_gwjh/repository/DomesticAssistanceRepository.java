package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.DegreeBindingEntity;
import com.dx.zjxz_gwjh.entity.DomesticAssistanceEntity;
import com.dx.zjxz_gwjh.vo.ZLBDomesticAssistanceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomesticAssistanceRepository extends JpaCommonRepository<DomesticAssistanceEntity, String> {

    List<DomesticAssistanceEntity> findByIdCard(String idCard);
}
