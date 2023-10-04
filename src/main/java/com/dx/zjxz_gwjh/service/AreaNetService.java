package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.*;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.QAreaNetEntity;
import com.dx.zjxz_gwjh.entity.QHighSchoolNetEntity;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.AreaCodeRepository;
import com.dx.zjxz_gwjh.repository.AreaNetRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AreaNetService extends JpaPublicService<AreaNetEntity, String> implements StandardService<AreaNetEntity, NetFilter, String> {
    @Autowired
    AreaNetRepository areaNetRepository;

    @Autowired
    AreaCodeRepository areaCodeRepository;

    public AreaNetService(AreaNetRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<AreaNetEntity> queryList(QueryRequest<NetFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        NetFilter filter = query.getFilter();
        if (filter != null) {
            QAreaNetEntity q = QAreaNetEntity.areaNetEntity;


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (org.springframework.util.StringUtils.hasText(keyword)) {
                predicate.and(q.name.contains(keyword));
            }
        }

        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public AreaNetEntity findByName(String name) throws ServiceException {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        AreaNetEntity areaNetEntity = areaNetRepository.findByName(name);
        if (areaNetEntity == null) {
            throw new ServiceException("该属地网格不存在，请先添加或修改属地网格信息");
        }
        return areaNetEntity;

    }

    public String findNameById(String areaNetId) {
        if (StringUtils.isBlank(areaNetId)) {
            return null;
        }
        AreaNetEntity areaNetEntity = areaNetRepository.findById(areaNetId).orElse(null);
        if (areaNetEntity == null) {
            return null;
        }
        return areaNetEntity.getName();
    }

    public List<NetActivityDto> getAreaNetActivityRanking() {
        return areaNetRepository.findAreaNetActivityRanking();
    }

    public List<AreaNetOverviewDto> getAreaNetOverview() {
        return areaNetRepository.findAreaNetOverview();
    }

    public List<TeacherStudentDto> getTeachersAndStudents(AreaRequestDto areaRequestDto) {
        List<Object[]> results = areaNetRepository.findTeachersAndStudents(areaRequestDto.getAreaId(), areaRequestDto.getGraduationYear(), areaRequestDto.getNetId());

        Map<String, TeacherStudentDto> teacherStudentMap = new HashMap<>();
        for (Object[] result : results) {
            String netId = (String) result[0]; // 假设 result[0] 包含 high_school_net_id
            String teacherName = (String) result[1]; // 假设 result[1] 包含老师名称
            String studentId = (String) result[2];
            String studentName = (String) result[3];

            // 使用 netId 和 teacherName 结合作为键
            String key = netId + "_" + teacherName;

            TeacherStudentDto teacherStudentDto = teacherStudentMap.get(key);
            if (teacherStudentDto == null) {
                teacherStudentDto = new TeacherStudentDto(teacherName, new ArrayList<>());
                teacherStudentMap.put(key, teacherStudentDto);
            }

            StudentDto studentDto = new StudentDto(studentId, studentName);
            teacherStudentDto.getStudentList().add(studentDto);
        }

        return new ArrayList<>(teacherStudentMap.values());
    }

    public List<AreaNetEntity> getAreaNetList(String id) {
        // 根据ID从AreaCodes表中获取area_code
        String areaCode = areaCodeRepository.findById(id).orElseThrow(() -> new RuntimeException("AreaId not found")).getCode();

        // 使用area_code查询AreaNetEntity
        return areaNetRepository.findByAreaCode(areaCode);
    }

    public AreaNetEntity findById(String areaNetId) throws ServiceException {
        if (StringUtils.isBlank(areaNetId)) {
            return null;
        }

        AreaNetEntity areaNetEntity = areaNetRepository.findById(areaNetId).orElse(null);
        if (areaNetEntity == null) {
            throw new ServiceException("该属地网格不存在，请先添加或修改属地网格信息");
        }
        return areaNetEntity;

    }
    }

//    public AreaNetEntity findOrCreateByNameAndContactorAndPhoneAndAreaCodeAndLocation(String areaNetName, String areaNetContactor, String areaNetContactorMobile, String areaNetAreaCode, String areaNetLocation) {
//        if (StringUtils.isBlank(areaNetName)) {
//            return null;
//        }
//        AreaNetEntity areaNetEntity = areaNetRepository.findByName(areaNetName);
//        if (areaNetEntity != null) {
//            areaNetEntity.setName(areaNetName);
//            areaNetEntity.setUserName(areaNetContactor) ;
//            areaNetEntity.setPhoneNumber(areaNetContactorMobile);
//            areaNetEntity.setAreaCode(areaNetAreaCode);
//            areaNetEntity.setLocation(areaNetLocation);
//            areaNetEntity = areaNetRepository.save(areaNetEntity);
//        } else {
//            areaNetEntity = new AreaNetEntity();
//            areaNetEntity.setName(areaNetName);
//            areaNetEntity.setUserName(areaNetContactor) ;
//            areaNetEntity.setPhoneNumber(areaNetContactorMobile);
//            areaNetEntity.setAreaCode(areaNetAreaCode);
//            areaNetEntity.setLocation(areaNetLocation);
//            areaNetEntity = areaNetRepository.save(areaNetEntity);
//        }
//        areaNetEntity = areaNetRepository.save(areaNetEntity); // 保存或更新实体
//        return areaNetEntity;
//    }

