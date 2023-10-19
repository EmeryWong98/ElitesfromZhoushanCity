package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.NetActivityDto;
import com.dx.zjxz_gwjh.dto.StudentsDto;
import com.dx.zjxz_gwjh.dto.UnionNetStudentsDto;
import com.dx.zjxz_gwjh.dto.UnionRequestDto;
import com.dx.zjxz_gwjh.entity.*;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.HighSchoolNetRepository;
import com.dx.zjxz_gwjh.repository.StudentsRepository;
import com.dx.zjxz_gwjh.repository.UnionNetRepository;
import com.dx.zjxz_gwjh.vo.StudentDetailsVO;
import com.dx.zjxz_gwjh.vo.StudentsDetailsVO;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnionNetService extends JpaPublicService<UnionNetEntity, String> implements StandardService<UnionNetEntity, NetFilter, String> {

    @Autowired
    UnionNetRepository unionNetRepository;

    @Autowired
    StudentsRepository studentsRepository;

    @Autowired
    DegreeBindingService degreeBindingService;

    public UnionNetService(UnionNetRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<UnionNetEntity> queryList(QueryRequest<NetFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        NetFilter filter = query.getFilter();
        if (filter != null) {
            QUnionNetEntity q = QUnionNetEntity.unionNetEntity;


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (org.springframework.util.StringUtils.hasText(keyword)) {
                predicate.and(q.name.contains(keyword));
            }
        }

        if (CollectionUtils.isEmpty(query.getSorts())) {
            query.setSorts(SortField.by("updateAt", true));
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }


    public UnionNetEntity findByName(String unionNetName) throws ServiceException {
        if (StringUtils.isBlank(unionNetName)) {
            return null;
        }

        UnionNetEntity unionNetEntity = unionNetRepository.findByName(unionNetName);
        if (unionNetEntity == null) {
            throw new ServiceException("该高校网格不存在，请先添加或修改高校网格信息");
        }
        return unionNetEntity;
    }

    public String findNameById(String unionNetId) {
        if (StringUtils.isBlank(unionNetId)) {
            return null;
        }

        UnionNetEntity unionNetEntity = unionNetRepository.findById(unionNetId).orElse(null);
        if (unionNetEntity == null) {
            return null;
        }
        return unionNetEntity.getName();
    }

    public List<NetActivityDto> getUnionNetActivityRanking() {
        return unionNetRepository.getUnionNetActivityRanking();
    }

    public UnionNetStudentsDto getUnionNetStudentsList(String id) {
        UnionNetEntity unionNet = unionNetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid unionNetId: " + id));

        UnionNetStudentsDto dto = new UnionNetStudentsDto();
        dto.setUserName(unionNet.getUserName());
        dto.setPhoneNumber(unionNet.getPhoneNumber());
        dto.setName(unionNet.getName());

        // 获取该网格的重点学子数量和学生详情
        List<StudentsEntity> students = studentsRepository.findByUnionNetId(unionNet.getId());
        dto.setKeyStudentsCount((int) students.stream().filter(StudentsEntity::getIsKeyContact).count());

        return dto;
    }


//    private StudentDetailsVO convertToDetailDto(StudentsEntity studentEntity) {
//        StudentDetailsVO dto = new StudentDetailsVO();
//        dto.setId(studentEntity.getId());
//        dto.setAcademicYear(studentEntity.getAcademicYear());
//        dto.setName(studentEntity.getName());
//        dto.setDob(studentEntity.getDob());
//        dto.setUniversityName(degreeBindingService.findHighestDegreeUniversityNameByStudentId(studentEntity.getId()));
//        dto.setMajor(degreeBindingService.findHighestDegreeMajorByStudentId(studentEntity.getId()));
//        dto.setArea(studentEntity.getArea());
//        dto.setPhone(studentEntity.getPhone());
//        dto.setIsKeyContact(studentEntity.getIsKeyContact());
//        return dto;
//    }

    public Integer getUnionNetCount() {
        return unionNetRepository.findAll().size();
    }

    public List<UnionRequestDto> getUnionNetList() {
        List<UnionNetEntity> unionNetEntities = unionNetRepository.findAll();
        List<UnionRequestDto> unionRequestDtos = new ArrayList<>();
        for (UnionNetEntity unionNetEntity : unionNetEntities) {
            UnionRequestDto unionRequestDto = new UnionRequestDto();
            unionRequestDto.setId(unionNetEntity.getId());
            unionRequestDto.setName(unionNetEntity.getName());
            unionRequestDto.setLon(unionNetEntity.getLon());
            unionRequestDto.setLat(unionNetEntity.getLat());
            unionRequestDto.setXorder(unionNetEntity.getXorder());
            unionRequestDto.setFiles(unionNetEntity.getFiles());
            unionRequestDto.setStatus(unionNetEntity.getStatus());
            unionRequestDto.setCreateTime(unionNetEntity.getCreateTime());
            unionRequestDtos.add(unionRequestDto);
        }
        return unionRequestDtos;
    }

    public UnionNetEntity findById(String unionNetId) throws ServiceException {
        if (StringUtils.isBlank(unionNetId)) {
            return null;
        }

        UnionNetEntity unionNetEntity = unionNetRepository.findById(unionNetId).orElse(null);
        if (unionNetEntity == null) {
            throw new ServiceException("该高校网格不存在，请先添加或修改高校网格信息");
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

    public List<UnionNetEntity> lists() {
        return unionNetRepository.findAll();
    }
}
