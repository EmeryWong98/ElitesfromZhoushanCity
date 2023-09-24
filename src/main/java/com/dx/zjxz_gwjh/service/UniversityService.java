package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.EliteMajorDto;
import com.dx.zjxz_gwjh.dto.EliteUniversityDto;
import com.dx.zjxz_gwjh.dto.EliteUniversityListDto;
import com.dx.zjxz_gwjh.entity.QUniversityEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.filter.UniversityFilter;
import com.dx.zjxz_gwjh.repository.UniversityRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UniversityService extends JpaPublicService<UniversityEntity, String> implements StandardService<UniversityEntity, UniversityFilter, String> {
    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private StudentsService studentsService;

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
        }

        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public UniversityEntity findOrCreateByNameAndProvince(String name, String province) throws ServiceException {
        UniversityEntity universityEntity = universityRepository.findByNameAndProvince(name, province);
        if (universityEntity == null) {
            // 如果没有找到，创建一个新的大学实体
            universityEntity = new UniversityEntity();
            universityEntity.setName(name);
            universityEntity.setProvince(province);

            // 保存到数据库
            universityRepository.save(universityEntity);
        }
        return universityEntity;
    }

    public EliteUniversityListDto getEliteUniversityList(StudentsFilter filter) throws ServiceException {
        EliteUniversityListDto result = new EliteUniversityListDto();

        // 设置 filter 以获取重点学生
        filter.setIsKeyContact(true);

        QueryRequest<StudentsFilter> queryRequest = new QueryRequest<>();
        queryRequest.setFilter(filter);

        // 从学生服务中获取符合条件的学生实体列表
        PagingData<StudentsEntity> students = studentsService.queryList(queryRequest);

        List<StudentsEntity> supremeStudents = students.getData().stream()
                .filter(s -> Boolean.TRUE.equals(s.getIsSupreme()))
                .collect(Collectors.toList());

        List<StudentsEntity> majorStudents = students.getData().stream()
                .filter(s -> Boolean.FALSE.equals(s.getIsSupreme()))
                .collect(Collectors.toList());

        // 填充 eliteUniversities 列表
        List<EliteUniversityDto> eliteUniversities = supremeStudents.stream()
                .collect(Collectors.groupingBy(s -> s.getUniversity().getName()))
                .entrySet()
                .stream()
                .map(e -> {
                    EliteUniversityDto dto = new EliteUniversityDto();
                    dto.setUniversityName(e.getKey());
                    dto.setKeyContactCount(e.getValue().size());
                    return dto;
                })
                .collect(Collectors.toList());

        result.setEliteUniversities(eliteUniversities);

        // 填充 eliteMajor 列表
        List<EliteMajorDto> eliteMajors = majorStudents.stream()
                .collect(Collectors.groupingBy(s -> new AbstractMap.SimpleEntry<>(s.getUniversity().getName(), s.getMajor())))
                .entrySet()
                .stream()
                .map(e -> {
                    EliteMajorDto dto = new EliteMajorDto();
                    dto.setUniversityName(e.getKey().getKey());
                    dto.setKeyContactCount(e.getValue().size());
                    dto.setMajorName(e.getKey().getValue());
                    return dto;
                })
                .collect(Collectors.toList());

        result.setEliteMajors(eliteMajors);

        return result;
    }



    public int count() {
        return (int) universityRepository.count();
    }


}
