package com.dx.zjxz_gwjh.service;

import com.dx.zjxz_gwjh.dto.AreaCodeDto;
import com.dx.zjxz_gwjh.dto.SimpleAreaCodeDto;
import com.dx.zjxz_gwjh.dto.StaticAreaCodeDto;
import com.dx.zjxz_gwjh.repository.AreaCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dx.zjxz_gwjh.entity.AreaCodeEntity;

import java.util.*;

@Service
public class AreaCodeService {
    @Autowired
    private AreaCodeRepository areaCodeRepository;

    public String getAreaCodeByName(String name) {
        return areaCodeRepository.findByName(name)
                .map(AreaCodeEntity::getCode)
                .orElse(null);
    }

    public List<StaticAreaCodeDto> getAreaCodeListStatic() {
        List<StaticAreaCodeDto> counties = Arrays.asList(
                new StaticAreaCodeDto("cbd46108d05c4c3393e5319edde692a9", "定海区", "3", new ArrayList<>()),
                new StaticAreaCodeDto("a31c792dbc504609ae275229ea1239f6", "普陀区", "3", new ArrayList<>()),
                new StaticAreaCodeDto("6f84ef6994a74ad4b7bd24d014409565", "岱山县", "3", new ArrayList<>()),
                new StaticAreaCodeDto("1f68bea42eae4d039d664328f560729c", "嵊泗县", "3", new ArrayList<>())
        );

        List<StaticAreaCodeDto> functionalAreas = Arrays.asList(
                new StaticAreaCodeDto("e9230ab250b34137bb8032ce653905a7", "新城", "3", new ArrayList<>()),
                new StaticAreaCodeDto("e9230ab250b34136bb9132ce653905a7", "普朱", "3", new ArrayList<>()),
                new StaticAreaCodeDto("e9230ab250b34149bb8032ce653905a7", "金塘", "3", new ArrayList<>()),
                new StaticAreaCodeDto("e9230ab250b34136bb9032ce653905b7", "六横", "3", new ArrayList<>())
        );

        StaticAreaCodeDto countyArea = new StaticAreaCodeDto("1f84ef6994a89ad4b7bd24d014409565", "县区", "2", counties);
        StaticAreaCodeDto functionalArea = new StaticAreaCodeDto("4h84ef6994a74ad4b7bd24d014409215", "功能区", "2", functionalAreas);

        return Arrays.asList(
                new StaticAreaCodeDto("f181ebd982654dc9a05d064d3197b0d0", "舟山市", "1", Arrays.asList(countyArea, functionalArea))
        );
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

    public List<SimpleAreaCodeDto> getSimpleAreaCodeList() {
        SimpleAreaCodeDto areaCodeDinghai = new SimpleAreaCodeDto("cbd46108d05c4c3393e5319edde692a9", "定海区");
        SimpleAreaCodeDto areaCodePutuo = new SimpleAreaCodeDto("a31c792dbc504609ae275229ea1239f6", "普陀区");
        SimpleAreaCodeDto areaCodeDaishan = new SimpleAreaCodeDto("6f84ef6994a74ad4b7bd24d014409565", "岱山县");
        SimpleAreaCodeDto areaCodeShengsi = new SimpleAreaCodeDto("1f68bea42eae4d039d664328f560729c", "嵊泗县");
        SimpleAreaCodeDto areaCodeXincheng = new SimpleAreaCodeDto("e9230ab250b34137bb8032ce653905a7", "新城");
        SimpleAreaCodeDto areaCodePuzhu = new SimpleAreaCodeDto("e9230ab250b34136bb9132ce653905a7", "普朱");
        SimpleAreaCodeDto areaCodeLiuhen = new SimpleAreaCodeDto("e9230ab250b34149bb8032ce653905a7", "六横");
        SimpleAreaCodeDto areaCodeJintang = new SimpleAreaCodeDto("e9230ab250b34136bb9032ce653905b7", "金塘");

        List<SimpleAreaCodeDto> areaCodeList = new ArrayList<>();
        areaCodeList.add(areaCodeDinghai);
        areaCodeList.add(areaCodePutuo);
        areaCodeList.add(areaCodeDaishan);
        areaCodeList.add(areaCodeShengsi);
        areaCodeList.add(areaCodeXincheng);
        areaCodeList.add(areaCodePuzhu);
        areaCodeList.add(areaCodeLiuhen);
        areaCodeList.add(areaCodeJintang);

        return areaCodeList;
    }

}
