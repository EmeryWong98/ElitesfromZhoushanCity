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
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.repository.AreaCodeRepository;
import com.dx.zjxz_gwjh.repository.StudentsRepository;
import com.dx.zjxz_gwjh.repository.UniversityRepository;
import com.dx.zjxz_gwjh.util.IdCardInfo;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentsService extends JpaPublicService<StudentsEntity, String> implements StandardService<StudentsEntity, StudentsFilter, String> {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private HighSchoolService highSchoolService;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private NetService netService;

    @Autowired
    private AreaCodeService areaCodeService;

    @Autowired
    private AreaCodeRepository areaCodeRepository;

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


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (StringUtils.hasText(keyword)) {
                predicate.and(q.name.contains(keyword)
                        .or(q.university.name.contains(keyword))
                        .or(q.highSchool.name.contains(keyword))
                        .or(q.major.contains(keyword)));
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
                predicate.and(q.university.name.contains(university));
            }

            // 专业
            String major = filter.getMajor();
            if (StringUtils.hasText(major)) {
                predicate.and(q.major.contains(major));
            }

            // 省份
            String province = filter.getProvince();
            if (StringUtils.hasText(province)) {
                predicate.and(q.province.eq(province));
            }

            // 属地ID
            String areaId = filter.getAreaId();
            if (StringUtils.hasText(areaId)) {
                // 通过属地ID获取属地名称
                String areaName = areaCodeService.findNameById(areaId); // 根据您的服务或存储库来调整
                if (StringUtils.hasText(areaName)) {
                    predicate.and(q.area.eq(areaName));
                }
            }


//            // 是否重点学子
//            Boolean isKeyContact = filter.getIsKeyContact();
//            if (isKeyContact != null) {
//                predicate.and(q.isKeyContact.eq(isKeyContact));
//            }

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

    @Override
    public StudentsEntity update(StudentsEntity entity) throws ServiceException {
        // 可以添加一些业务逻辑，比如根据entity里面的某些字段更改其他字段

        // 调用父类方法进行实际的更新
        entity = super.update(entity);

        return entity;
    }

    public StudentsEntity createStudent(StudentsDto dto) throws ServiceException {
        // 如果是新创建的学生（ID 为 null），检查 idCard 的唯一性
        if (dto.getId() == null) {
            StudentsEntity existingStudent = studentsRepository.findByIdCard(dto.getIdCard());
            if (existingStudent != null) {
                throw new ServiceException("学生重复");
            }
        }

        // 先检查并获取或创建University实体
        UniversityEntity universityEntity = universityService.findOrCreateByNameAndProvince(dto.getUniversityName(), dto.getUniversityProvince());

        // 先检查并获取或创建HighSchool实体
        HighSchoolEntity highSchoolEntity = highSchoolService.findOrCreateByName(dto.getHighSchoolName());

        // 创建或获取现有的学生实体
        StudentsEntity entity = new StudentsEntity();
        if (dto.getId() != null) {
            entity = this.getById(dto.getId());
        }

        // 将DTO中的数据复制到学生实体
        ObjectUtils.copyEntity(dto, entity);

        // 设置关联的University和HighSchool
        entity.setUniversity(universityEntity);
        entity.setHighSchool(highSchoolEntity);


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

        // 创建或更新学生实体
        StudentsEntity studentEntity;
        if (dto.getId() != null) {
            studentEntity = this.update(entity); // 假设的更新方法，您需要实现它
        } else {
            studentEntity = this.create(entity); // 假设的创建方法，您需要实现它
        }

        // 如果成功创建或更新了学生实体，处理与网格的关系
        if (studentEntity != null) {
            netService.createOrUpdateStudentNetRelation(studentEntity, dto);
        }

        return studentEntity;
    }

    public Map<String, Map<String, Integer>> getStudentCountByAreaAndProvince() {
        List<StudentsEntity> students = studentsRepository.findAll();

        Map<String, String> universityIdToProvince = new HashMap<>();
        for (UniversityEntity university : universityRepository.findAll()) {
            universityIdToProvince.put(university.getId(), university.getProvince());
        }

        Map<String, Map<String, Integer>> areaProvinceCountMap = new HashMap<>();

        for (StudentsEntity student : students) {
            String area = student.getArea();
            String universityId = student.getUniversityId();
            String province = universityIdToProvince.getOrDefault(universityId, "Unknown");

            areaProvinceCountMap
                    .computeIfAbsent(area, k -> new HashMap<>())
                    .put(province, areaProvinceCountMap.get(area).getOrDefault(province, 0) + 1);

            areaProvinceCountMap
                    .computeIfAbsent("舟山市", k -> new HashMap<>())
                    .put(province, areaProvinceCountMap.get("舟山市").getOrDefault(province, 0) + 1);
        }

        for (Map.Entry<String, Map<String, Integer>> areaEntry : areaProvinceCountMap.entrySet()) {
            Map<String, Integer> provinceCount = areaEntry.getValue();
            int totalCount = 0;
            for (int count : provinceCount.values()) {
                totalCount += count;
            }
            provinceCount.put("总人数", totalCount);
        }

        return areaProvinceCountMap;
    }

    public List<StudentCountDto> getStudentCountByAcademicYearAndArea(AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        int startYear = academicYearAndAreaDto.getStartYear();
        int endYear = academicYearAndAreaDto.getEndYear();
        String areaId = academicYearAndAreaDto.getAreaId();

        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();
        List<AreaCodeDto> selectedAreas = findSelectedAndChildAreas(allAreas, areaId);

        List<StudentCountDto> result = new ArrayList<>();
        for (AreaCodeDto area : selectedAreas) {
            int count = calculateAreaCount(area, startYear, endYear);
            StudentCountDto dto = new StudentCountDto();
            dto.setId(area.getId());
            dto.setName(area.getName());
            dto.setCount(count);
            result.add(dto);
        }

        return result;
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
        idMap.put("e9230ab250b34136bb8032ce653905a8", Arrays.asList("e9230ab250b34136bb8032ce653905a8", "e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34137bb8032ce653905a7", Arrays.asList("e9230ab250b34136bb8032ce653905a8", "e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34136bb9132ce653905a7", Arrays.asList("e9230ab250b34136bb8032ce653905a8", "e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34149bb8032ce653905a7", Arrays.asList("e9230ab250b34136bb8032ce653905a8", "e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));
        idMap.put("e9230ab250b34136bb9032ce653905b7", Arrays.asList("e9230ab250b34136bb8032ce653905a8", "e9230ab250b34137bb8032ce653905a7", "e9230ab250b34136bb9132ce653905a7", "e9230ab250b34149bb8032ce653905a7", "e9230ab250b34136bb9032ce653905b7"));

        List<String> areaIds = idMap.getOrDefault(areaId, Collections.singletonList(areaId));
        List<AreaCodeDto> allAreas = areaCodeService.getAreaCodeList();

        List<StudentCountDto> result = new ArrayList<>();
        for (String id : areaIds) {
            List<AreaCodeDto> selectedAreas = findSelectedAndChildAreas(allAreas, id);
            for (AreaCodeDto area : selectedAreas) {
                int count = calculateAreaCount(area, startYear, endYear);
                StudentCountDto dto = new StudentCountDto();
                dto.setId(area.getId());
                dto.setName(area.getName());
                dto.setCount(count);
                result.add(dto);
            }
        }

        return result;
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



        for (AreaCodeDto area : selectedAreas) {
            MapCountDto mapCountDto = new MapCountDto();
            mapCountDto.setId(area.getId());
            mapCountDto.setName(area.getName());

            int totalStudentCountInAllProvinces = 0; // 初始化总学子数
            List<String> selectedAreaNames = getAreaNames(area); // 获取选定区域的名字集合

            // 步骤2: 获取区域内所有省份
            List<String> provinces = getProvincesInAreaAndYearRange(area, academicYearAndAreaDto.getStartYear(), academicYearAndAreaDto.getEndYear());

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
                int schoolCount = studentsRepository.countSchoolsByProvinceAndYearRange(
                        province, startYear, endYear, selectedAreaNames
                );
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


        for (int year = startYear; year <= endYear; year++) {
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








    public Map<String, Map<String, Object>> getKeyContactCountByAreaAndAcademicYear() {
        List<String> allAreas = studentsRepository.findAllDistinctAreas();

        Map<String, Map<String, Object>> resultMap = new HashMap<>();
        Map<String, Integer> totalMap = new HashMap<>();

        for (String area : allAreas) {
            List<Object[]> counts = studentsRepository.countKeyContactsByAreaAndAcademicYear(area);
            Map<String, Integer> academicYearMap = new HashMap<>();
            int areaTotal = 0;
            for (Object[] count : counts) {
                String academicYear = String.valueOf(count[0]);
                int num = ((Number) count[1]).intValue();
                academicYearMap.put(academicYear, num);
                areaTotal += num;

                // 更新总数
                totalMap.put(academicYear, totalMap.getOrDefault(academicYear, 0) + num);
            }

            // 计算占比并添加到结果
            Map<String, Object> areaResult = new HashMap<>();
            areaResult.put("counts", academicYearMap);
            Map<String, Double> proportionMap = new HashMap<>();
            for (Map.Entry<String, Integer> entry : academicYearMap.entrySet()) {
                proportionMap.put(entry.getKey(), entry.getValue() * 100.0 / areaTotal);
            }
            areaResult.put("proportion", proportionMap);
            resultMap.put(area, areaResult);
        }

        // 添加总数到结果
        int grandTotal = totalMap.values().stream().mapToInt(Integer::intValue).sum();
        Map<String, Double> totalProportionMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : totalMap.entrySet()) {
            totalProportionMap.put(entry.getKey(), entry.getValue() * 100.0 / grandTotal);
        }
        Map<String, Object> totalResult = new HashMap<>();
        totalResult.put("counts", totalMap);
        totalResult.put("proportion", totalProportionMap);
        resultMap.put("舟山市", totalResult);

        return resultMap;
    }


    public long getTotalCount() {
        return studentsRepository.count();
    }

    public Map<String, Map<String, Map<String, Integer>>> getKeyContactCountByAreaProvinceAndUniversity() {
        List<StudentsEntity> students = studentsRepository.findByIsKeyContactTrue();

        Map<String, String> universityIdToProvince = new HashMap<>();
        for (UniversityEntity university : universityRepository.findAll()) {
            universityIdToProvince.put(university.getId(), university.getProvince());
        }

        Map<String, Map<String, Set<String>>> areaToProvinceToUniversities = new HashMap<>();
        Map<String, Map<String, Map<String, Integer>>> areaProvinceCountMap = new HashMap<>();

        for (StudentsEntity student : students) {
            String area = student.getArea();
            String universityId = student.getUniversityId();
            String province = universityIdToProvince.getOrDefault(universityId, "Unknown");

            areaProvinceCountMap
                    .computeIfAbsent(area, k -> new HashMap<>())
                    .computeIfAbsent(province, k -> new HashMap<>())
                    .put("人数", areaProvinceCountMap.get(area).get(province).getOrDefault("人数", 0) + 1);

            areaToProvinceToUniversities
                    .computeIfAbsent(area, k -> new HashMap<>())
                    .computeIfAbsent(province, k -> new HashSet<>())
                    .add(universityId);
        }

        Map<String, Map<String, Integer>> zhoushanTotalMap = new HashMap<>();

        for (Map.Entry<String, Map<String, Map<String, Integer>>> areaEntry : areaProvinceCountMap.entrySet()) {
            String area = areaEntry.getKey();
            Map<String, Map<String, Integer>> provinceCountMap = areaEntry.getValue();

            int totalPeopleInArea = 0;

            Map<String, Set<String>> provinceToUniversities = areaToProvinceToUniversities.getOrDefault(area, new HashMap<>());
            for (Map.Entry<String, Map<String, Integer>> provinceEntry : provinceCountMap.entrySet()) {
                String province = provinceEntry.getKey();
                Map<String, Integer> provinceMap = provinceEntry.getValue();

                totalPeopleInArea += provinceMap.getOrDefault("人数", 0);

                if (provinceToUniversities.containsKey(province)) {
                    provinceMap.put("高校数量", provinceToUniversities.get(province).size());
                }

                Map<String, Integer> zhoushanProvinceMap = zhoushanTotalMap.computeIfAbsent(province, k -> new HashMap<>());
                zhoushanProvinceMap.put("人数", zhoushanProvinceMap.getOrDefault("人数", 0) + provinceMap.getOrDefault("人数", 0));
                zhoushanProvinceMap.put("高校数量", zhoushanProvinceMap.getOrDefault("高校数量", 0) + provinceMap.getOrDefault("高校数量", 0));
            }

            Map<String, Integer> totalMap = new HashMap<>();
            totalMap.put("总人数", totalPeopleInArea);
            provinceCountMap.put("总计", totalMap);
        }

        int zhoushanTotalPeople = 0;
        for (Map<String, Integer> provinceData : zhoushanTotalMap.values()) {
            zhoushanTotalPeople += provinceData.getOrDefault("人数", 0);
        }
        Map<String, Integer> zhoushanTotalCountMap = new HashMap<>();
        zhoushanTotalCountMap.put("总人数", zhoushanTotalPeople);
        zhoushanTotalMap.put("总计", zhoushanTotalCountMap);

        areaProvinceCountMap.put("舟山市", zhoushanTotalMap);

        return areaProvinceCountMap;
    }

    public Map<String, Integer> getKeyContactCountByAcademicYearAndArea(AcademicYearDto academicYearDto) throws ServiceException {
        int startYear = academicYearDto.getStartYear();
        int endYear = academicYearDto.getEndYear();

        // 检查年份有效性
        if (endYear < startYear) {
            throw new ServiceException("结束年份不能小于开始年份");
        }

        // 获取所有可能的属地
        List<String> allAreas = studentsRepository.findAllDistinctAreas();

        Map<String, Integer> areaCountMap = new HashMap<>();

        for (String area : allAreas) {
            int count = studentsRepository.findByAcademicYearBetweenAndAreaAndIsKeyContactTrue(startYear, endYear, area).size();
            areaCountMap.put(area, count);
        }

        // 计算所有属地的总数
        int totalCount = areaCountMap.values().stream().mapToInt(Integer::intValue).sum();
        areaCountMap.put("舟山市", totalCount);

        return areaCountMap;
    }


    public int count() {
        return (int) studentsRepository.count();
    }
}
