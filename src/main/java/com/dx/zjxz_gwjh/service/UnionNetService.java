package com.dx.zjxz_gwjh.service;

import com.dx.zjxz_gwjh.entity.UnionNetEntity;
import com.dx.zjxz_gwjh.repository.UnionNetRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnionNetService {

    @Autowired
    UnionNetRepository unionNetRepository;

    public UnionNetEntity findOrCreateByName(String unionNetName) {
        if (StringUtils.isBlank(unionNetName)) {
            return null;
        }

        UnionNetEntity unionNetEntity = unionNetRepository.findByName(unionNetName);
        if (unionNetEntity == null) {
            unionNetEntity = new UnionNetEntity();
            unionNetEntity.setName(unionNetName);
            unionNetEntity = unionNetRepository.save(unionNetEntity);
        }
        return unionNetEntity;
    }

    public UnionNetEntity findOrCreateByNameAndContactorAndPhoneAndLocation(String unionNetName, String unionNetContactor, String unionNetContactorMobile, String unionNetLocation) {
        if (StringUtils.isBlank(unionNetName)) {
            return null;
        }
        UnionNetEntity unionNetEntity = unionNetRepository.findByName(unionNetName);
        if (unionNetEntity != null) {
            unionNetEntity = new UnionNetEntity();
            unionNetEntity.setName(unionNetName);
            unionNetEntity.setUserName(unionNetContactor);
            unionNetEntity.setPhoneNumber(unionNetContactorMobile);
            unionNetEntity.setLocation(unionNetLocation);
            unionNetEntity = unionNetRepository.save(unionNetEntity);
        } else {
            unionNetEntity = new UnionNetEntity();
            unionNetEntity.setName(unionNetName);
            unionNetEntity.setUserName(unionNetContactor);
            unionNetEntity.setPhoneNumber(unionNetContactorMobile);
            unionNetEntity.setLocation(unionNetLocation);
            unionNetEntity = unionNetRepository.save(unionNetEntity);
        }
        unionNetEntity = unionNetRepository.save(unionNetEntity); // 保存或更新实体
        return unionNetEntity;
    }
}
