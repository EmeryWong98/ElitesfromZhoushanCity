package com.dx.zjxz_gwjh.service;

import com.dx.zjxz_gwjh.entity.OfficerNetEntity;
import com.dx.zjxz_gwjh.repository.OfficerNetRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficerNetService {
    @Autowired
    OfficerNetRepository officerNetRepository;

    public OfficerNetEntity findOrCreateByName(String officerNetName) {
        OfficerNetEntity officerNetEntity = officerNetRepository.findByUserName(officerNetName);
        if (officerNetEntity == null) {
            officerNetEntity = new OfficerNetEntity();
            officerNetEntity.setUserName(officerNetName);
            officerNetEntity = officerNetRepository.save(officerNetEntity);
        }
        return officerNetEntity;
    }

    public OfficerNetEntity findOrCreateByNameAndTitle(String officerNetName, String officerNetPosition) {
        if (StringUtils.isBlank(officerNetName)) {
            return null;
        }
        OfficerNetEntity officerNetEntity = officerNetRepository.findByUserName(officerNetName);
        if (officerNetEntity != null) {
            officerNetEntity.setUserName(officerNetName);
            officerNetEntity.setTitle(officerNetPosition);
            officerNetEntity = officerNetRepository.save(officerNetEntity);
        } else {
            officerNetEntity = new OfficerNetEntity();
            officerNetEntity.setUserName(officerNetName);
            officerNetEntity.setTitle(officerNetPosition);
            officerNetEntity = officerNetRepository.save(officerNetEntity);
        }
        officerNetEntity = officerNetRepository.save(officerNetEntity); // 保存或更新实体
        return officerNetEntity;
    }
}
