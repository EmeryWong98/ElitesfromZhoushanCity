package com.dx.zjxz_gwjh.repository;

import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.zjxz_gwjh.entity.DegreeBindingEntity;
import com.dx.zjxz_gwjh.entity.DomesticAssistanceEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface DomesticAssistanceRepository extends JpaCommonRepository<DomesticAssistanceEntity, String> {

}
