package com.dx.zjxz_gwjh.service;

import com.dx.zjxz_gwjh.dto.AreaCodeDto;
import com.dx.zjxz_gwjh.repository.AreaCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dx.zjxz_gwjh.entity.AreaCodeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AreaCodeService {
    @Autowired
    private AreaCodeRepository areaCodeRepository;

    public String getAreaCodeByName(String name) {
        return areaCodeRepository.findByName(name)
                .map(AreaCodeEntity::getCode)
                .orElse(null);
    }

    public List<AreaCodeDto> getAreaCodeListSingle() {
        List<AreaCodeEntity> entities = areaCodeRepository.findAll();
        return convertToDtoSingle(entities);
    }

    private List<AreaCodeDto> convertToDtoSingle(List<AreaCodeEntity> entities) {
        Map<String, AreaCodeDto> dtoMap = new HashMap<>();
        List<AreaCodeDto> result = new ArrayList<>();

        // Convert each entity to DTO and store it in the map
        for (AreaCodeEntity entity : entities) {
            AreaCodeDto dto = new AreaCodeDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setLevel(entity.getLevel());
            dto.setChildren(new ArrayList<>());
            dtoMap.put(entity.getCode(), dto); // Use entity's code as the key

            if (entity.getParentCode() == null) {
                result.add(dto);
            }
        }

        // Set children for each dto
        for (AreaCodeEntity entity : entities) {
            if (entity.getParentCode() != null) {
                AreaCodeDto parent = dtoMap.get(entity.getParentCode()); // Now this should work correctly
                AreaCodeDto child = dtoMap.get(entity.getCode()); // Use entity's code here as well
                if (parent != null && child != null) {
                    parent.getChildren().add(child);
                }
            }
        }

        return result;
    }

    public List<AreaCodeDto> getAreaCodeList() {
        List<AreaCodeEntity> entities = areaCodeRepository.findAll();
        return convertToDto(entities);
    }

    private List<AreaCodeDto> convertToDto(List<AreaCodeEntity> entities) {
        Map<String, AreaCodeDto> dtoMap = new HashMap<>();
        List<AreaCodeDto> result = new ArrayList<>();

        // Convert each entity to DTO and store it in the map
        for (AreaCodeEntity entity : entities) {
            AreaCodeDto dto = new AreaCodeDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setLevel(entity.getLevel());
            dto.setChildren(new ArrayList<>());
            dtoMap.put(entity.getCode(), dto); // Use entity's code as the key
            result.add(dto); // Add every area to the result list
        }

        // Set children for each dto
        for (AreaCodeEntity entity : entities) {
            if (entity.getParentCode() != null) {
                AreaCodeDto parent = dtoMap.get(entity.getParentCode());
                AreaCodeDto child = dtoMap.get(entity.getCode());
                if (parent != null && child != null) {
                    parent.getChildren().add(child);
                }
            }
        }

        return result;
    }

    public List<String> findNamesByIds(List<String> areaIds) {
        return areaCodeRepository.findNamesById(areaIds);
    }

    public String findNameById(String areaId) {
        return areaCodeRepository.findNameById(areaId);
    }
}
