package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.*;
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.QHighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.*;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HighSchoolNetService extends JpaPublicService<HighSchoolNetEntity, String> implements StandardService<HighSchoolNetEntity, NetFilter, String> {
    @Autowired
    HighSchoolNetRepository highSchoolNetRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private AreaCodeRepository areaCodeRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private HighSchoolRepository highSchoolRepository;

    public HighSchoolNetService(HighSchoolNetRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<HighSchoolNetEntity> queryList(QueryRequest<NetFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        NetFilter filter = query.getFilter();
        if (filter != null) {
            QHighSchoolNetEntity q = QHighSchoolNetEntity.highSchoolNetEntity;


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (org.springframework.util.StringUtils.hasText(keyword)) {
                predicate.and(q.name.contains(keyword));
            }

            // 网格所属高中
            String location = filter.getLocation();
            if (org.springframework.util.StringUtils.hasText(location)) {
                predicate.and(q.location.eq(location));
            }
        }

        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public HighSchoolNetEntity findByName(String name) throws ServiceException {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        HighSchoolNetEntity highSchoolNetEntity = highSchoolNetRepository.findByName(name);
        if (highSchoolNetEntity == null) {
            throw new ServiceException("该高中网格不存在，请先添加或修改高中网格信息");
        }
        return highSchoolNetEntity;
    }

    public String findNameById(String highSchoolNetId) {
        if (StringUtils.isBlank(highSchoolNetId)) {
            return null;
        }

        HighSchoolNetEntity highSchoolNetEntity = highSchoolNetRepository.findById(highSchoolNetId).orElse(null);
        if (highSchoolNetEntity == null) {
            return null;
        }
        return highSchoolNetEntity.getName();
    }


    public HighSchoolNetEntity findOrCreateByNameAndContactorAndPhoneAndAreaCodeAndLocation(String highSchoolNetName, String highSchoolNetContactor, String highSchoolNetContactorMobile, String highSchoolNetAreaCode, String highSchoolNetLocation) {
        if (StringUtils.isBlank(highSchoolNetName)) {
            return null;
        }

        HighSchoolNetEntity highSchoolNetEntity = highSchoolNetRepository.findByName(highSchoolNetName);
        if (highSchoolNetEntity != null) {
            // 如果找到了实体，就更新其他字段
            highSchoolNetEntity.setUserName(highSchoolNetContactor);
            highSchoolNetEntity.setPhoneNumber(highSchoolNetContactorMobile);
            highSchoolNetEntity.setAreaCode(highSchoolNetAreaCode);
            highSchoolNetEntity.setLocation(highSchoolNetLocation);
        } else {
            // 如果没有找到实体，就创建新的实体
            highSchoolNetEntity = new HighSchoolNetEntity();
            highSchoolNetEntity.setName(highSchoolNetName);
            highSchoolNetEntity.setUserName(highSchoolNetContactor);
            highSchoolNetEntity.setPhoneNumber(highSchoolNetContactorMobile);
            highSchoolNetEntity.setAreaCode(highSchoolNetAreaCode);
            highSchoolNetEntity.setLocation(highSchoolNetLocation);
        }
        highSchoolNetEntity = highSchoolNetRepository.save(highSchoolNetEntity); // 保存或更新实体
        return highSchoolNetEntity;
    }

    public List<HighSchoolNetOverviewDto> getHighSchoolNetOverview() {
        return studentsRepository.findHighSchoolNetOverview();
    }

    public List<NetActivityDto> getHighSchoolNetActivityRanking() {
        return highSchoolNetRepository.findHighSchoolNetActivityRanking();
    }

    public List<HighSchoolNetSimpleOverviewDto> getHighSchoolNetSimpleOverview() {
        return studentsRepository.findHighSchoolNetSimpleOverview();
    }

    public List<TeacherStudentDto> getTeachersAndStudents(HighSchoolRequestDto highSchoolRequestDto) {
        List<Object[]> results = studentsRepository.findTeachersAndStudents(highSchoolRequestDto.getHighSchoolId(), highSchoolRequestDto.getGraduationYear(), highSchoolRequestDto.getNetId());

        Map<String, TeacherStudentDto> teacherStudentMap = new HashMap<>();
        for (Object[] result : results) {
            String netId = (String) result[0]; // 假设 result[0] 包含 high_school_net_id
            String teacherName = (String) result[1]; // 假设 result[1] 包含老师名称
            String studentId = (String) result[2];
            String studentName = (String) result[3];
            String studentSex = (String) result[4];

            // 使用 netId 和 teacherName 结合作为键
            String key = netId + "_" + teacherName;

            TeacherStudentDto teacherStudentDto = teacherStudentMap.get(key);
            if (teacherStudentDto == null) {
                teacherStudentDto = new TeacherStudentDto(teacherName, new ArrayList<>());
                teacherStudentMap.put(key, teacherStudentDto);
            }

            StudentDto studentDto = new StudentDto(studentId, studentName, studentSex);
            teacherStudentDto.getStudentList().add(studentDto);
        }

        return new ArrayList<>(teacherStudentMap.values());
    }


    public List<HighSchoolNetEntity> getHighSchoolNetList(String id){

        HighSchoolEntity highSchoolEntity = highSchoolRepository.findById(id).orElse(null);

        List<HighSchoolNetEntity> highSchoolNetEntityList = highSchoolNetRepository.findByLocationOrderByName(highSchoolEntity.getName());

        return highSchoolNetEntityList;

    }

    public HighSchoolNetEntity findById(String highSchoolNetId) throws ServiceException{
        if (StringUtils.isBlank(highSchoolNetId)) {
            return null;
        }

        HighSchoolNetEntity highSchoolNetEntity = highSchoolNetRepository.findById(highSchoolNetId).orElse(null);
        if (highSchoolNetEntity == null) {
            throw new ServiceException("该高中网格不存在，请先添加或修改高中网格信息");
        }
        return highSchoolNetEntity;
}
}
