package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.*;
import com.dx.zjxz_gwjh.entity.*;
import com.dx.zjxz_gwjh.enums.DegreeType;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.repository.*;
import com.dx.zjxz_gwjh.util.IdCardInfo;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import org.elasticsearch.cluster.metadata.AliasAction;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.print.Pageable;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static java.util.Collections.max;

@Service
public class StudentsService extends JpaPublicService<StudentsEntity, String> implements StandardService<StudentsEntity, StudentsFilter, String> {

    @Lazy
    @Autowired
    private UniversityService universityService;

    @Autowired
    private HighSchoolService highSchoolService;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private DegreeBindingRepository degreeBindingRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private HighSchoolNetService highSchoolNetService;

    @Autowired
    private AreaNetService areaNetService;

    @Autowired
    private OfficerNetService officerNetService;

    @Autowired
    private UnionNetService UnionNetService;

    @Autowired
    private AreaCodeService areaCodeService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private HighSchoolNetRepository highSchoolNetRepository;

    @Autowired
    private HighSchoolRepository highSchoolRepository;

    public StudentsService(StudentsRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<StudentsEntity> queryList(QueryRequest<StudentsFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        StudentsFilter filter = query.getFilter();
        if (filter != null) {
            QStudentsEntity q = QStudentsEntity.studentsEntity;
            QDegreeBindingEntity qDegree = QDegreeBindingEntity.degreeBindingEntity;
            QUniversityEntity qUniversity = QUniversityEntity.universityEntity;

            // Create a base query
            JPAQuery<StudentsEntity> jpaQuery = new JPAQuery<>(entityManager);
            jpaQuery.from(q)
                    .leftJoin(qDegree).on(q.id.eq(qDegree.studentId))
                    .leftJoin(qUniversity).on(qDegree.universityId.eq(qUniversity.id))
                    .where(predicate);  // assuming predicate is built using the new Q classes as needed.

            // 关键词搜索
            String keyword = filter.getKeyword();
            if (StringUtils.hasText(keyword)) {
                predicate.and(q.name.contains(keyword)
                        .or(qUniversity.name.contains(keyword))
                        .or(q.highSchool.name.contains(keyword)));
            }

            // 学生姓名
            String name = filter.getName();
            if (StringUtils.hasText(name)) {
                predicate.and(q.name.contains(name));
            }

            // 性别
            String sex = filter.getSex();
            if (StringUtils.hasText(sex)) {
                predicate.and(q.sex.eq(sex));
            }

            // 高中
            String highSchool = filter.getHighSchool();
            if (StringUtils.hasText(highSchool)) {
                predicate.and(q.highSchool.name.contains(highSchool));
            }

            // 大学
            String university = filter.getUniversity();
            if (StringUtils.hasText(university)) {
                predicate.and(q.universities.any().name.contains(university));
            }

            //专业
            String major = filter.getMajor();
            if (StringUtils.hasText(major)) {
                predicate.and(q.degreeBindings.any().major.contains(major));
            }

            // 省份
            String province = filter.getProvince();
            if (StringUtils.hasText(province)) {
                predicate.and(q.universities.any().province.eq(province));
            }


            // 学年
            Integer academicYear = filter.getAcademicYear();
            if (academicYear != null) {
                predicate.and(q.academicYear.eq(academicYear));
            }

            // 学历
            String degree = filter.getDegree();
            if (StringUtils.hasText(degree)) {
                predicate.and(q.degreeBindings.any().degree.eq(DegreeType.fromDescription(degree)));
            }

            //属地
            String area = filter.getArea();
            if (StringUtils.hasText(area)) {
                predicate.and(q.area.contains(area));
            }

            // 属地ID
            String areaId = filter.getAreaId();
            if (StringUtils.hasText(areaId)) {
                List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();
                List<AreaCodeDto> selectedAndChildAreas = findSelectedAndChildAreas(allAreas, areaId);

                List<String> allAreaNames = selectedAndChildAreas.stream()
                        .map(AreaCodeDto::getName) // 使用名称而不是ID
                        .collect(Collectors.toList());

                predicate.and(q.area.in(allAreaNames)); // 使用名称列表
            }

            // 学联网格ID
            String unionNetId = filter.getUnionNetId();
            if (StringUtils.hasText(unionNetId)) {
                predicate.and(q.unionNet.id.eq(unionNetId));
            }


            // 是否重点学子
            Boolean isKeyContact = filter.getIsKeyContact();
            if (isKeyContact != null) {
                predicate.and(q.isKeyContact.eq(isKeyContact));
            }

            //是否回舟
            Boolean isBack = filter.getIsBack();
            if (isBack != null) {
                predicate.and(q.isBack.eq(isBack));
            }

            Integer startYear = filter.getStartYear();
            Integer endYear = filter.getEndYear();

            if (startYear != null) {
                predicate.and(q.academicYear.goe(startYear));
            }

            if (endYear != null) {
                predicate.and(q.academicYear.loe(endYear));
            }
        }


        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public StudentsEntity createStudent(StudentsCreateDto dto) throws ServiceException {
        // 如果是新创建的学生（ID 为 null），检查 idCard 的唯一性
        StudentsEntity entity = new StudentsEntity();
        StudentsEntity existingStudent = studentsRepository.findByIdCard(dto.getIdCard());
            if (existingStudent != null) {
                throw new ServiceException("学生重复");
            } else {

            // 先检查并获取HighSchool实体
            HighSchoolEntity highSchoolEntity = highSchoolService.findById(dto.getHighSchoolId());

            // 先检查并获取highSchoolNet实体
            HighSchoolNetEntity highSchoolNetEntity = highSchoolNetService.findById(dto.getHighSchoolNetId());

            // 先检查并获取或创建AreaNet实体
            AreaNetEntity areaNetEntity = areaNetService.findById(dto.getAreaNetId());

            // 先检查并获取或创建OfficerNet实体
            OfficerNetEntity officerNetEntity = officerNetService.findById(dto.getOfficerNetId());

            // 先检查并获取或创建UnionNet实体
            UnionNetEntity unionNetEntity = UnionNetService.findById(dto.getUnionNetId());

            // 将DTO中的数据复制到学生实体
            ObjectUtils.copyEntity(dto, entity);

            // 设置关联的University和HighSchool
            entity.setHighSchool(highSchoolEntity);
            entity.setHighSchoolNet(highSchoolNetEntity);
            entity.setAreaNet(areaNetEntity);
            entity.setOfficerNet(officerNetEntity);
            entity.setUnionNet(unionNetEntity);

            // 从身份证中提取出生日期和性别
            try {
                java.util.Date birthDate = IdCardInfo.getBirthDate(dto.getIdCard()); // 假设 IdCardInfo 类存在
                java.sql.Date sqlBirthDate = new java.sql.Date(birthDate.getTime());  // 转换为 java.sql.Date
                String gender = IdCardInfo.getGender(dto.getIdCard());     // 假设 IdCardInfo 类存在

                entity.setDob(sqlBirthDate); // 假设您的 StudentsEntity 有一个叫做 'dob' 的字段
                entity.setSex(gender); // 假设您的 StudentsEntity 有一个叫做 'gender' 的字段
            } catch (ParseException e) {
                throw new ServiceException("身份证格式错误");
            }
        }

        // 保存学生实体到数据库
        studentsRepository.save(entity);

    // 为每个大学和学位创建和保存一个 DegreeBindingEntity 对象
    List<String> universityNames = Arrays.asList(dto.getUniversity1Name(), dto.getUniversity2Name(), dto.getUniversity3Name());
    List<String> degrees = Arrays.asList(dto.getDegree1(), dto.getDegree2(), dto.getDegree3());
    List<String> majors = Arrays.asList(dto.getMajor1(), dto.getMajor2(), dto.getMajor3());
    List<String> universityProvinces = Arrays.asList(dto.getUniversity1Province(), dto.getUniversity2Province(), dto.getUniversity3Province());

    for (int i = 0; i<universityNames.size();i++) {
        String universityName = universityNames.get(i);
        String province = universityProvinces.get(i);
        String degree = degrees.get(i);
        String major = majors.get(i);

        // 如果degree为空或者是空字符串，直接跳过这次循环
        if (!StringUtils.hasText(degree)) {
            continue;
        }

        if (universityName != null) {
            UniversityEntity universityEntity = universityService.findOrCreateByNameAndProvince(universityName, province);

            if (universityEntity != null) {
                DegreeBindingEntity degreeBindingEntity = new DegreeBindingEntity();
                degreeBindingEntity.setStudentId(entity.getId()); // 使用已保存的entity的ID
                degreeBindingEntity.setUniversityId(universityEntity.getId());
                degreeBindingEntity.setMajor(major);

                try {
                    degreeBindingEntity.setDegree(DegreeType.fromDescription(degree));
                } catch (ServiceException e) {
                    throw new ServiceException("无效的学位描述: " + degree);
                }

                degreeBindingRepository.save(degreeBindingEntity);
            }
        }
    }
    return entity;
}


    public StudentsEntity updateStudents(StudentsDto dto) throws ServiceException {
        StudentsEntity entity = this.getById(dto.getId());

        if(entity == null) {
            throw new ServiceException("学生不存在");
        }

        // 将DTO中的数据复制到学生实体
        ObjectUtils.copyEntity(dto, entity);

        if (dto.getHighSchoolNetId() != null) {
            HighSchoolNetEntity highSchoolNet = highSchoolNetRepository.findById(dto.getHighSchoolNetId()).orElse(null);
            entity.setHighSchoolNet(highSchoolNet);
        }

        if (dto.getHighSchoolId() != null) {
            HighSchoolEntity highSchool = highSchoolRepository.findById(dto.getHighSchoolId()).orElse(null);
            entity.setHighSchool(highSchool);
        }

        if (dto.getAreaNetId() != null) {
            AreaNetEntity areaNet = areaNetService.findById(dto.getAreaNetId());
            entity.setAreaNet(areaNet);
        }

        if (dto.getOfficerNetId() != null) {
            OfficerNetEntity officerNet = officerNetService.findById(dto.getOfficerNetId());
            entity.setOfficerNet(officerNet);
        }

        if (dto.getUnionNetId() != null) {
            UnionNetEntity unionNet = UnionNetService.findById(dto.getUnionNetId());
            entity.setUnionNet(unionNet);
        }


        // 先检查大学名称是否改变
        List<String> universityNames = Arrays.asList(dto.getUniversity1Name(), dto.getUniversity2Name(), dto.getUniversity3Name());
        List<String> degrees = Arrays.asList(dto.getDegree1(), dto.getDegree2(), dto.getDegree3());
        List<String> majors = Arrays.asList(dto.getMajor1(), dto.getMajor2(), dto.getMajor3());
        List<String> universityProvinces = Arrays.asList(dto.getUniversity1Province(), dto.getUniversity2Province(), dto.getUniversity3Province());

        List<DegreeBindingEntity> degreeBindingEntities = degreeBindingRepository.findByStudentId(entity.getId());
        List<String> existingUniversityNames = degreeBindingEntities.stream()
                .map(DegreeBindingEntity::getUniversityId)
                .map(universityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(UniversityEntity::getName)
                .collect(Collectors.toList());

        List<String> existingDegrees = degreeBindingEntities.stream()
                .map(DegreeBindingEntity::getDegree)
                .map(DegreeType::getDescription)
                .collect(Collectors.toList());

        List<String> existingMajors = degreeBindingEntities.stream()
                .map(DegreeBindingEntity::getMajor)
                .collect(Collectors.toList());


        // 如果大学名称改变了，删除现有的 DegreeBindingEntity 对象
        if(!existingUniversityNames.equals(universityNames)) {
            degreeBindingRepository.deleteAll(degreeBindingEntities);
        }

        //如果学位改变了，删除现有的 DegreeBindingEntity 对象
        if(!existingDegrees.equals(degrees)) {
            degreeBindingRepository.deleteAll(degreeBindingEntities);
        }

        //如果专业改变了，删除现有的 DegreeBindingEntity 对象
        if(!existingMajors.equals(majors)) {
            degreeBindingRepository.deleteAll(degreeBindingEntities);
        }

        // 为每个大学和学位创建和保存一个 DegreeBindingEntity 对象
        for (int i = 0; i<universityNames.size();i++) {
            String universityName = universityNames.get(i);
            String province = universityProvinces.get(i);
            String degree = degrees.get(i);
            String major = majors.get(i);

        // 如果degree为空或者是空字符串，直接跳过这次循环
            if (!StringUtils.hasText(degree)) {
                continue;
            }

            if (universityName != null) {
                UniversityEntity universityEntity = universityService.findOrCreateByNameAndProvince(universityName, province);

                if (universityEntity != null) {
                    DegreeBindingEntity degreeBindingEntity = new DegreeBindingEntity();
                    degreeBindingEntity.setStudentId(entity.getId()); // 使用已保存的entity的ID
                    degreeBindingEntity.setUniversityId(universityEntity.getId());
                    degreeBindingEntity.setMajor(major);

                    try {
                        degreeBindingEntity.setDegree(DegreeType.fromDescription(degree));
                    } catch (ServiceException e) {
                        throw new ServiceException("无效的学位描述: " + degree);
                    }

                    degreeBindingRepository.save(degreeBindingEntity);
                }
            }
        }

        return entity;
    }

    @Transactional
    public void deleteById(String id) throws ServiceException {
        // 检查学生是否存在
        if (!studentsRepository.existsById(id)) {
            throw new ServiceException("Student with id: " + id + " does not exist");
        }

        // 删除相关联的DegreeBindingEntity
        degreeBindingRepository.deleteByStudentId(id);

        // 删除StudentsEntity
        studentsRepository.deleteById(id);
    }



//    public List<StudentCountDto> getStudentCountByAcademicYearAndArea(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
//        int startYear = academicYearAndAreaDto.getStartYear();
//        int endYear = academicYearAndAreaDto.getEndYear();
//        String areaId = academicYearAndAreaDto.getAreaId();
//
//        if (endYear < startYear) {
//            throw new ServiceException("结束年份不能小于开始年份");
//        }
//
//        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();
//        List<AreaCodeDto> selectedAreas = findSelectedAndChildAreas(allAreas, areaId);
//
//        List<StudentCountDto> result = new ArrayList<>();
//        for (AreaCodeDto area : selectedAreas) {
//            int count = calculateAreaCount(area, startYear, endYear);
//            StudentCountDto dto = new StudentCountDto();
//            dto.setId(area.getId());
//            dto.setName(area.getName());
//            dto.setCount(count);
//            result.add(dto);
//        }
//
//        return result;
//    }
    public List<StudentCountDto> getStudentCountByAcademicYearAndArea(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        int startYear = academicYearAndAreaDto.getStartYear();
        int endYear = academicYearAndAreaDto.getEndYear();
        String areaId = academicYearAndAreaDto.getAreaId();

        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();
        List<AreaCodeDto> selectedAreas = findSelectedAndChildAreas(allAreas, areaId);

        StudentCountDto studentCountDto = null;
        for (AreaCodeDto area : selectedAreas) {
            if(area.getId().equals(areaId)) {
                int count = calculateAreaCount(area, startYear, endYear);
                studentCountDto = new StudentCountDto();
                studentCountDto.setId(area.getId());
                studentCountDto.setName(area.getName());
                studentCountDto.setCount(count);
                break; // Once we found the matching area, no need to continue the loop.
            }
        }

        if(studentCountDto == null) {
            return Collections.emptyList();
        }

        return Collections.singletonList(studentCountDto);
    }


    public List<StudentCountDto> getStudentCountByAcademicYearAndAreaDefault(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        int startYear = academicYearAndAreaDto.getStartYear();
        int endYear = academicYearAndAreaDto.getEndYear();
        String areaId = academicYearAndAreaDto.getAreaId();

        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        Map<String, List<String>> idMap = new HashMap<>();
        idMap.put("f181ebd982654dc9a05d064d3197b0d0", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("6f84ef6994a74ad4b7bd24d014409565", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("1f68bea42eae4d039d664328f560729c", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("a31c792dbc504609ae275229ea1239f6", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("cbd46108d05c4c3393e5319edde692a9", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("e9230ab250b34137bb8032ce653905a7", Arrays.asList("e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34136bb9132ce653905a7", Arrays.asList("e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34149bb8032ce653905a7", Arrays.asList("e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34136bb9032ce653905b7", Arrays.asList("e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));

        List<String> areaIds = idMap.getOrDefault(areaId, Collections.singletonList(areaId));
        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();

        List<StudentCountDto> result = new ArrayList<>();
        for (String id : areaIds) {
            // 对于idMap中的每个id，只计算这个区域本身的计数，不再计算其子区域的计数
            AreaCodeDto selectedArea = findAreaById(allAreas, id);
            if(selectedArea != null) {
                int count = calculateAreaCount(selectedArea, startYear, endYear);
                StudentCountDto dto = new StudentCountDto();
                dto.setId(selectedArea.getId());
                dto.setName(selectedArea.getName());
                dto.setCount(count);
                result.add(dto);
            }
        }

        return result;
    }

    private AreaCodeDto findAreaById(List<AreaCodeDto> areas, String id) {
        for (AreaCodeDto area : areas) {
            if(area.getId().equals(id)) {
                return area;
            }
        }
        return null;
    }

    public List<MapCountDto> getMapCountByAcademicYearAndArea(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        int startYear = academicYearAndAreaDto.getStartYear();
        int endYear = academicYearAndAreaDto.getEndYear();
        List<MapCountDto> result = new ArrayList<>();

        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        // 步骤1: 获取选定区域和其子区域
        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();
        List<AreaCodeDto> selectedAreas = findSelectedAndChildAreas(allAreas, academicYearAndAreaDto.getAreaId());



        MapCountDto mapCountDto = null;
        for (AreaCodeDto area : selectedAreas) {
            if(area.getId().equals(academicYearAndAreaDto.getAreaId())) {
                mapCountDto = new MapCountDto();
                mapCountDto.setId(area.getId());
                mapCountDto.setName(area.getName());

                int totalStudentCountInAllProvinces = 0; // 初始化总学子数
                List<String> selectedAreaNames = getAreaNames(area); // 获取选定区域的名字集合

                // 步骤2: 获取区域内所有省份
                List<String> provinces = universityRepository.findDistinctProvince();

                // 步骤3: 统计每个省份的数据
                List<ProvinceCountDto> provinceCountList = new ArrayList<>();
                for (String province : provinces) {
                    ProvinceCountDto provinceCountDto = new ProvinceCountDto();
                    provinceCountDto.setName(province);

                    // 计算学子总数
                    int studentCount = studentsRepository.countStudentsByProvinceAndYearRange(
                            province, startYear, endYear, selectedAreaNames
                    );
                    provinceCountDto.setCount(studentCount);
                    totalStudentCountInAllProvinces += studentCount; // 计算总学子数

                    // 计算学校数量
                    int schoolCount = universityRepository.countByProvince(province);

                    provinceCountDto.setSchoolCount(schoolCount);

                    provinceCountList.add(provinceCountDto);
                }

                for (ProvinceCountDto provinceCountDto : provinceCountList) {
                    double percentage;
                    if (totalStudentCountInAllProvinces > 0) {
                        percentage = (double) provinceCountDto.getCount() / totalStudentCountInAllProvinces * 100;
                    } else {
                        percentage = 0;
                    }
                    provinceCountDto.setPercentage(String.format("%.2f%%", percentage));

                    // 根据百分比计算rateLevel
                    if (percentage <= 1) {
                        provinceCountDto.setRateLevel("0");
                    } else if (percentage <= 10) {
                        provinceCountDto.setRateLevel("1");
                    } else  {
                        provinceCountDto.setRateLevel("2");
                    }
                }

                mapCountDto.setProvinceCountList(provinceCountList);
                result.add(mapCountDto);
            }
        }

            if(mapCountDto == null) {
                throw new ServiceException("无法找到匹配的区域");
            }

        // 步骤5: 返回结果
        return result;
    }

    public List<YearlyStudentCountDto> getYearlyStudentCount(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        int startYear = academicYearAndAreaDto.getStartYear();
        int endYear = academicYearAndAreaDto.getEndYear();
        List<YearlyStudentCountDto> result = new ArrayList<>();

        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();
        List<AreaCodeDto> selectedAreas = findSelectedAndChildAreas(allAreas, academicYearAndAreaDto.getAreaId());

        List<String> areaNames = selectedAreas.stream()
                .map(AreaCodeDto::getName) // 或者 getId，取决于您想传递什么给查询
                .collect(Collectors.toList());


        for (int year = endYear; year >= startYear; year--) {
            YearlyStudentCountDto yearlyCount = new YearlyStudentCountDto();
            yearlyCount.setYear(year);

            int count = studentsRepository.countStudentsByYearAndAreas(year, areaNames);
            yearlyCount.setCount(count);

            result.add(yearlyCount);
        }

        return result;
    }


    private List<String> getAreaNames(AreaCodeDto area) {
        List<String> names = new ArrayList<>();
        names.add(area.getName()); // 添加当前区域的名字

        // 如果有子区域，则递归地获取其名字
        for (AreaCodeDto childArea : area.getChildren()) {
            names.addAll(getAreaNames(childArea));
        }

        return names;
    }

    private List<String> getProvincesInAreaAndYearRange(AreaCodeDto area, int startYear, int endYear) {
        // 步骤1: 收集区域IDs
        List<String> areaIds = collectAreaIds(area);

        // 步骤2: 查询数据库
        List<String> areaNames = areaCodeService.findNamesByIds(areaIds);
        List<String> provinces = studentsRepository.findProvincesByAreaNamesAndYearRange(areaNames, startYear, endYear);

        // 返回省份列表
        return provinces;
    }

    private List<String> collectAreaIds(AreaCodeDto area) {
        List<String> ids = new ArrayList<>();
        ids.add(area.getId()); // 添加自己的ID
        for(AreaCodeDto child : area.getChildren()) {
            ids.addAll(collectAreaIds(child)); // 递归添加子区域的ID
        }
        return ids;
    }

    private List<AreaCodeDto> findSelectedAndChildAreas(List<AreaCodeDto> allAreas, String areaId) {
        for (AreaCodeDto area : allAreas) {
            if (area.getId().equals(areaId)) {
                return findAllChildren(area);
            }
        }
        return new ArrayList<>();
    }

    private List<AreaCodeDto> findAllChildren(AreaCodeDto parentArea) {
        List<AreaCodeDto> children = new ArrayList<>();
        children.add(parentArea); // 添加父区域本身
        for (AreaCodeDto child : parentArea.getChildren()) {
            children.addAll(findAllChildren(child)); // 递归添加所有子区域
        }
        return children;
    }

    private int calculateAreaCount(AreaCodeDto area, int startYear, int endYear) {
        int count = studentsRepository.countByAcademicYearBetweenAndArea(startYear, endYear, area.getName());
        for (AreaCodeDto child : area.getChildren()) {
            count += calculateAreaCount(child, startYear, endYear); // 递归计算所有子区域的学生总数
        }
        return count;
    }

    private int calculateAreaCountForElite(AreaCodeDto area, int startYear, int endYear, boolean isKeyContact) {
        int count = studentsRepository.countByAcademicYearBetweenAndAreaAndIsKeyContact(startYear, endYear, area.getName(), isKeyContact);
        for (AreaCodeDto child : area.getChildren()) {
            count += calculateAreaCountForElite(child, startYear, endYear, isKeyContact); // 递归计算所有子区域的学生总数
        }
        return count;
    }

    private int calculateAreaCountForDegree(AreaCodeDto area, int startYear, int endYear, DegreeType degreeT) {
        int count = studentsRepository.countByAcademicYearBetweenAndAreaAndDegreeAndIsKeyContact(startYear, endYear, area.getName(), degreeT);
        for (AreaCodeDto child : area.getChildren()) {
            count += calculateAreaCountForDegree(child, startYear, endYear, degreeT); // 递归计算所有子区域的学生总数，考虑学位和是否为重点学子
        }
        return count;
    }















    public List<EliteCountDto> getKeyStudentCountByAcademicYearAndArea(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        int startYear = academicYearAndAreaDto.getStartYear();
        int endYear = academicYearAndAreaDto.getEndYear();
        String areaId = academicYearAndAreaDto.getAreaId();

        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();
        List<AreaCodeDto> selectedAreas = findSelectedAndChildAreas(allAreas, areaId);

        EliteCountDto eliteCountDto = null;
        for (AreaCodeDto area : selectedAreas) {
            if(area.getId().equals(areaId)) {
                int count = calculateAreaCountForElite(area, startYear, endYear, true); // 重点学子数量
                int scount = calculateAreaCountForDegree(area, startYear, endYear, DegreeType.Graduate); // 硕士数量
                int bcount = calculateAreaCountForDegree(area, startYear, endYear, DegreeType.PHD); // 博士数量
                eliteCountDto = new EliteCountDto();
                eliteCountDto.setId(area.getId());
                eliteCountDto.setName(area.getName());
                eliteCountDto.setCount(count);
                eliteCountDto.setScount(scount);
                eliteCountDto.setBcount(bcount);
                break; // 一旦我们找到了匹配的区域，就不需要继续循环了。
            }
        }

        if(eliteCountDto == null) {
            return Collections.emptyList();
        }

        return Collections.singletonList(eliteCountDto);
    }

    public List<EliteCountDto> getKeyStudentCountByAcademicYearAndAreaDefault(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        int startYear = academicYearAndAreaDto.getStartYear();
        int endYear = academicYearAndAreaDto.getEndYear();
        String areaId = academicYearAndAreaDto.getAreaId();

        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        Map<String, List<String>> idMap = new HashMap<>();
        idMap.put("f181ebd982654dc9a05d064d3197b0d0", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("6f84ef6994a74ad4b7bd24d014409565", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("1f68bea42eae4d039d664328f560729c", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("a31c792dbc504609ae275229ea1239f6", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("cbd46108d05c4c3393e5319edde692a9", Arrays.asList("6f84ef6994a74ad4b7bd24d014409565", "1f68bea42eae4d039d664328f560729c", "a31c792dbc504609ae275229ea1239f6", "cbd46108d05c4c3393e5319edde692a9"));
        idMap.put("e9230ab250b34137bb8032ce653905a7", Arrays.asList("e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34136bb9132ce653905a7", Arrays.asList("e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34149bb8032ce653905a7", Arrays.asList("e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34136bb9032ce653905b7", Arrays.asList("e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));

        List<String> areaIds = idMap.getOrDefault(areaId, Collections.singletonList(areaId));
        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();

        List<EliteCountDto> result = new ArrayList<>();
        for (String id : areaIds) {
            Optional<AreaCodeDto> optionalArea = allAreas.stream().filter(area -> area.getId().equals(id)).findFirst();
            if(optionalArea.isPresent()) {
                AreaCodeDto area = optionalArea.get();
                int count = calculateAreaCountForElite(area, startYear, endYear, true); // 重点学子数量
                int scount = calculateAreaCountForDegree(area, startYear, endYear, DegreeType.Graduate); // 硕士数量
                int bcount = calculateAreaCountForDegree(area, startYear, endYear, DegreeType.PHD); // 博士数量
                EliteCountDto dto = new EliteCountDto();
                dto.setId(area.getId());
                dto.setName(area.getName());
                dto.setCount(count);
                dto.setScount(scount);
                dto.setBcount(bcount);
                result.add(dto);
            }
        }

        return result;
    }

    public List<MapCountDto> getKeyMapCountByAcademicYearAndArea(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        int startYear = academicYearAndAreaDto.getStartYear();
        int endYear = academicYearAndAreaDto.getEndYear();
        List<MapCountDto> result = new ArrayList<>();

        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        // 步骤1: 获取选定区域和其子区域
        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();
        List<AreaCodeDto> selectedAreas = findSelectedAndChildAreas(allAreas, academicYearAndAreaDto.getAreaId());



        MapCountDto mapCountDto = null;
        for (AreaCodeDto area : selectedAreas) {
            if(area.getId().equals(academicYearAndAreaDto.getAreaId())) {
                mapCountDto = new MapCountDto();
                mapCountDto.setId(area.getId());
                mapCountDto.setName(area.getName());

                int totalStudentCountInAllProvinces = 0; // 初始化总学子数
                List<String> selectedAreaNames = getAreaNames(area); // 获取选定区域的名字集合

                // 步骤2: 获取区域内所有省份
                List<String> provinces = universityRepository.findDistinctProvince();

                // 步骤3: 统计每个省份的数据
                List<ProvinceCountDto> provinceCountList = new ArrayList<>();
                for (String province : provinces) {
                    ProvinceCountDto provinceCountDto = new ProvinceCountDto();
                    provinceCountDto.setName(province);

                    // 计算学子总数
                    int studentCount = studentsRepository.countKeyStudentsByProvinceAndYearRange(
                            province, startYear, endYear, selectedAreaNames, true
                    );
                    provinceCountDto.setCount(studentCount);
                    totalStudentCountInAllProvinces += studentCount; // 计算总学子数

                    // 计算学校数量
                    int schoolCount = universityRepository.countKeyUniversitiesByProvince(province);

                    provinceCountDto.setSchoolCount(schoolCount);

                    provinceCountList.add(provinceCountDto);
                }

                for (ProvinceCountDto provinceCountDto : provinceCountList) {
                    double percentage;
                    if (totalStudentCountInAllProvinces > 0) {
                        percentage = (double) provinceCountDto.getCount() / totalStudentCountInAllProvinces * 100;
                    } else {
                        percentage = 0;
                    }
                    provinceCountDto.setPercentage(String.format("%.2f%%", percentage));

                    // 根据百分比计算rateLevel
                    if (percentage <= 1) {
                        provinceCountDto.setRateLevel("0");
                    } else if (percentage <= 5) {
                        provinceCountDto.setRateLevel("1");
                    } else if (percentage <= 10) {
                        provinceCountDto.setRateLevel("2");
                    } else {
                        provinceCountDto.setRateLevel("3");
                    }
                }

                mapCountDto.setProvinceCountList(provinceCountList);
                result.add(mapCountDto);
            }
        }

        if(mapCountDto == null) {
            throw new ServiceException("无法找到匹配的区域");
        }
        // 步骤5: 返回结果
        return result;
    }

    public List<YearlyStudentCountDto> getKeyYearlyStudentCount(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        int startYear = academicYearAndAreaDto.getStartYear();
        int endYear = academicYearAndAreaDto.getEndYear();
        List<YearlyStudentCountDto> result = new ArrayList<>();

        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();
        List<AreaCodeDto> selectedAreas = findSelectedAndChildAreas(allAreas, academicYearAndAreaDto.getAreaId());

        List<String> areaNames = selectedAreas.stream()
                .map(AreaCodeDto::getName) // 或者 getId，取决于您想传递什么给查询
                .collect(Collectors.toList());


        for (int year = endYear; year >= startYear; year--) {
            YearlyStudentCountDto yearlyCount = new YearlyStudentCountDto();
            yearlyCount.setYear(year);

            int count = studentsRepository.countKeyStudentsByYearAndAreas(year, areaNames);
            yearlyCount.setCount(count);

            result.add(yearlyCount);
        }

        return result;
    }



    public int count() {
        return (int) studentsRepository.count();
    }

    public StudentsEntity MassiveCreateStudent(StudentsImportDto dto) throws ServiceException {
        // 如果是新创建的学生（ID 为 null），检查 idCard 的唯一性
        StudentsEntity entity = new StudentsEntity();
        StudentsEntity existingStudent = studentsRepository.findByIdCard(dto.getIdCard());
        if (existingStudent != null) {
            throw new ServiceException("学生重复");
        } else {

        // 先检查并获取或创建HighSchool实体
        HighSchoolEntity highSchoolEntity = highSchoolService.findOrCreateByName(dto.getHighSchoolName());

        // 先检查并获取或创建highSchoolNet实体
        HighSchoolNetEntity highSchoolNetEntity = highSchoolNetService.findOrCreateByNameAndContactorAndPhoneAndAreaCodeAndLocation(dto.getHighSchoolNetName(), dto.getHighSchoolNetContactor(), dto.getHighSchoolNetContactorMobile(), dto.getHighSchoolNetAreaCode(), dto.getHighSchoolNetLocation());

        // 先检查并获取或创建AreaNet实体
        AreaNetEntity areaNetEntity = areaNetService.findOrCreateByNameAndContactorAndPhoneAndAreaCodeAndLocation(dto.getAreaNetName(), dto.getAreaNetContactor(), dto.getAreaNetContactorMobile(), dto.getAreaNetAreaCode(), dto.getAreaNetLocation());

        // 先检查并获取或创建OfficerNet实体
        OfficerNetEntity officerNetEntity = officerNetService.findOrCreateByNameAndTitle(dto.getOfficerNetName(),dto.getOfficerNetPosition());

        // 先检查并获取或创建UnionNet实体
        UnionNetEntity unionNetEntity = UnionNetService.findByName(dto.getUnionNetName());

        // 将DTO中的数据复制到学生实体
        ObjectUtils.copyEntity(dto, entity);

        // 设置关联的University和HighSchool
        entity.setHighSchool(highSchoolEntity);
        entity.setHighSchoolNet(highSchoolNetEntity);
        entity.setAreaNet(areaNetEntity);
        entity.setOfficerNet(officerNetEntity);
        entity.setUnionNet(unionNetEntity);


        // 从身份证中提取出生日期和性别
        try {
            java.util.Date birthDate = IdCardInfo.getBirthDate(dto.getIdCard()); // 假设 IdCardInfo 类存在
            java.sql.Date sqlBirthDate = new java.sql.Date(birthDate.getTime());  // 转换为 java.sql.Date
            String gender = IdCardInfo.getGender(dto.getIdCard());     // 假设 IdCardInfo 类存在

            entity.setDob(sqlBirthDate); // 假设您的 StudentsEntity 有一个叫做 'dob' 的字段
            entity.setSex(gender); // 假设您的 StudentsEntity 有一个叫做 'gender' 的字段
        } catch (ParseException e) {
            throw new ServiceException("身份证格式错误");
        }

            // 保存学生实体到数据库
            studentsRepository.save(entity);

            // 为每个大学和学位创建和保存一个 DegreeBindingEntity 对象
            String universityName = dto.getUniversityName();
            String province = dto.getUniversityProvince();
            String degree = dto.getDegree();
            String major = dto.getMajor();

            if (universityName != null) {
                UniversityEntity universityEntity = universityService.findOrCreateByNameAndProvince(universityName, province);

                if (universityEntity != null) {
                    DegreeBindingEntity degreeBindingEntity = new DegreeBindingEntity();
                    degreeBindingEntity.setStudentId(entity.getId()); // 使用已保存的entity的ID
                    degreeBindingEntity.setUniversityId(universityEntity.getId());
                    degreeBindingEntity.setMajor(major);

                    try {
                        degreeBindingEntity.setDegree(DegreeType.fromDescription(degree));
                    } catch (ServiceException e) {
                        throw new ServiceException("无效的学位描述: " + degree);
                    }

                    degreeBindingRepository.save(degreeBindingEntity);
                }
            }
        }
        return entity;
    }

    public StudentsEntity findByIdCard(String idCard) {
        return studentsRepository.findByIdCard(idCard);
    }
}
