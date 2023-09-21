package com.dx.zjxz_gwjh.service;

import com.dx.zjxz_gwjh.dto.NetNameDto;
import com.dx.zjxz_gwjh.dto.StudentsDto;
import com.dx.zjxz_gwjh.entity.NetEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.StudentsNetBindingEntity;
import com.dx.zjxz_gwjh.enums.NetType;
import com.dx.zjxz_gwjh.repository.NetRepository;
import com.dx.zjxz_gwjh.repository.StudentsNetBindingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetService {
    @Autowired
    private NetRepository netRepository;

    @Autowired
    private StudentsNetBindingRepository studentsNetBindingRepository;

    public void createOrUpdateStudentNetRelation(StudentsEntity studentEntity, StudentsDto dto) {
        List<NetNameDto> netNames = dto.getNetNames();
        for (NetNameDto netNameDto : netNames) {
            String netName = netNameDto.getName();
            // 查找或创建网格实体
            NetEntity netEntity = netRepository.findByName(netName).orElseGet(() -> {
                NetEntity newNet = new NetEntity();
                newNet.setName(netName);
                newNet.setType(netNameDto.getType());
                newNet.setUserName(netNameDto.getUserName());
                newNet.setPhoneNumber(netNameDto.getPhoneNumber());
                newNet.setAreaCode(netNameDto.getAreaCode());
                // 设置其他必要的字段
                return netRepository.save(newNet);
            });

            // 在关联表中创建或更新记录
            StudentsNetBindingEntity bindingEntity = new StudentsNetBindingEntity();
            bindingEntity.setStudentId(studentEntity.getId());
            bindingEntity.setNetId(netEntity.getId());
            // 设置其他必要的字段
            studentsNetBindingRepository.save(bindingEntity);
        }
}
}
