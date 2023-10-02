package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.zjxz_gwjh.entity.OfficerNetEntity;
import com.dx.zjxz_gwjh.repository.OfficerNetRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficerNetService {
    @Autowired
    OfficerNetRepository officerNetRepository;

    public OfficerNetEntity findByName(String officerNetName) throws ServiceException{
        if (StringUtils.isBlank(officerNetName)) {
            return null;
        }

        OfficerNetEntity officerNetEntity = officerNetRepository.findByUserName(officerNetName);
        if (officerNetEntity == null) {
            throw new ServiceException("该党政领导不存在，请先添加或修改党政领导信息");
        }
        return officerNetEntity;
    }

    public String findNameById(String officerNetId) {
        if (StringUtils.isBlank(officerNetId)) {
            return null;
        }

        OfficerNetEntity officerNetEntity = officerNetRepository.findById(officerNetId).orElse(null);
        if (officerNetEntity == null) {
            return null;
        }
        return officerNetEntity.getUserName();
    }

//    public OfficerNetEntity findOrCreateByNameAndTitle(String officerNetName, String officerNetPosition) {
//        if (StringUtils.isBlank(officerNetName)) {
//            return null;
//        }
//        OfficerNetEntity officerNetEntity = officerNetRepository.findByUserName(officerNetName);
//        if (officerNetEntity != null) {
//            officerNetEntity.setUserName(officerNetName);
//            officerNetEntity.setTitle(officerNetPosition);
//            officerNetEntity = officerNetRepository.save(officerNetEntity);
//        } else {
//            officerNetEntity = new OfficerNetEntity();
//            officerNetEntity.setUserName(officerNetName);
//            officerNetEntity.setTitle(officerNetPosition);
//            officerNetEntity = officerNetRepository.save(officerNetEntity);
//        }
//        officerNetEntity = officerNetRepository.save(officerNetEntity); // 保存或更新实体
//        return officerNetEntity;
//    }
}
