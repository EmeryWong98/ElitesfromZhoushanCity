package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.easyspringweb.storage.models.StorageObject;
import com.dx.zjxz_gwjh.dto.EliteMajorDto;
import com.dx.zjxz_gwjh.dto.EliteUniversityDto;
import com.dx.zjxz_gwjh.dto.EliteUniversityListDto;
import com.dx.zjxz_gwjh.dto.UniversitiesImportDto;
import com.dx.zjxz_gwjh.entity.QUniversityEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.filter.UniversityFilter;
import com.dx.zjxz_gwjh.repository.StudentsRepository;
import com.dx.zjxz_gwjh.repository.UniversityRepository;
import com.dx.zjxz_gwjh.util.UniversityMajorDictionary;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UniversityService extends JpaPublicService<UniversityEntity, String> implements StandardService<UniversityEntity, UniversityFilter, String> {
    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private StudentsRepository studentsRepository;

    public UniversityService(UniversityRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<UniversityEntity> queryList(QueryRequest<UniversityFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        UniversityFilter filter = query.getFilter();
        if (filter != null) {
            QUniversityEntity q = QUniversityEntity.universityEntity;


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (StringUtils.hasText(keyword)) {
                predicate.and(q.name.contains(keyword)
                        .or(q.province.contains(keyword)));
            }

            // 省份搜索
            String province = filter.getProvince();
            if (StringUtils.hasText(province)) {
                predicate.and(q.province.eq(province));
            }

            // 是否重点大学搜索
            Boolean isSupreme = filter.getIsSupreme();
            if (isSupreme != null) {
                predicate.and(q.isSupreme.eq(isSupreme));
            }

            // 是否重点专业搜索
            Boolean isKeyMajor = filter.getIsKeyMajor();
            if (isKeyMajor != null) {
                predicate.and(q.isKeyMajor.eq(isKeyMajor));
            }
        }

        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public UniversityEntity findOrCreateByNameAndProvince(String name, String province) throws ServiceException {
        if (name == null || name.trim().isEmpty()) {
            throw new ServiceException("大学名称不能为空");
        }

        UniversityEntity universityEntity = universityRepository.findByName(name.trim());
        if (universityEntity == null) {
            // 如果没有找到，创建一个新的大学实体
            universityEntity = new UniversityEntity();
            universityEntity.setName(name.trim());
            universityEntity.setIsSupreme(false);
            universityEntity.setIsKeyMajor(false);
            universityEntity.setProvince(province);


            // 保存到数据库
            universityRepository.save(universityEntity);
        }
        return universityEntity;
    }

    public EliteUniversityListDto getEliteUniversityList(StudentsFilter filter) throws ServiceException {
        EliteUniversityListDto result = new EliteUniversityListDto();

        // 获取符合条件的重点大学列表
        List<UniversityEntity> supremeUniversities = universityRepository.findByIsSupremeAndProvince(true, filter.getProvince());
        List<EliteUniversityDto> eliteUniversities = supremeUniversities.stream().map(university -> {
            EliteUniversityDto dto = new EliteUniversityDto();
            dto.setUniversityName(university.getName());
            dto.setLogo(university.getFiles()); // 设置 logo
            // 计算每个大学的学生总数
            int studentCount = studentsRepository.countByUniversityIdAndAcademicYearBetween(university.getId(), filter.getStartYear(), filter.getEndYear());
            dto.setKeyContactCount(studentCount);
            return dto;
        }).collect(Collectors.toList());
        result.setEliteUniversities(eliteUniversities);

        // 获取符合条件的重点专业大学列表
        List<UniversityEntity> keyMajorUniversities = universityRepository.findByIsKeyMajorAndProvince(true, filter.getProvince());
        Map<String, List<String>> majorNameDictionary = UniversityMajorDictionary.MAJOR_DICTIONARY;

        List<EliteMajorDto> eliteMajors = keyMajorUniversities.stream().flatMap(university -> {
            List<String> majors = majorNameDictionary.get(university.getName());
            if (majors != null) {
                return majors.stream().map(major -> {
                    EliteMajorDto dto = new EliteMajorDto();
                    dto.setUniversityName(university.getName());
                    dto.setLogo(university.getFiles()); // 设置 logo
                    // 计算每个大学、每个专业的关键联系人人数
                    int keyContactCount = studentsRepository.countByUniversityIdAndMajorAndIsSupremeAndIsKeyContactAndAcademicYearBetween(
                            university.getId(), major, false, true, filter.getStartYear(), filter.getEndYear()
                    );
                    dto.setKeyContactCount(keyContactCount);
                    dto.setMajorName(major);
                    return dto;
                });
            } else {
                return Stream.empty();
            }
        }).collect(Collectors.toList());
        result.setEliteMajors(eliteMajors);

        return result;
    }







    public int count() {
        return (int) universityRepository.count();
    }


//    public UniversityEntity massiveCreateUniversity(UniversitiesImportDto dto) throws ServiceException {
//        // 检查大学名称的唯一性
//        UniversityEntity existingUniversity = universityRepository.findByName(dto.getName());
//
//        // 创建或获取现有的大学实体
//        UniversityEntity entity;
//        if (existingUniversity != null) { // 如果存在同名大学，则进行更新
//            entity = existingUniversity;
//        } else { // 否则创建新的实体
//            entity = new UniversityEntity();
//        }
//
//        // 将DTO中的数据复制到大学实体
//        ObjectUtils.copyEntity(dto, entity);
//
//        // 创建或更新大学实体
//        UniversityEntity universityEntity;
//        if (existingUniversity != null) { // 如果存在同名大学，则进行更新
//            universityEntity = this.update(entity); // 假设的更新方法，您需要实现它
//        } else { // 否则进行创建
//            universityEntity = this.create(entity); // 假设的创建方法，您需要实现它
//        }
//
//        return universityEntity;
//    }

}
