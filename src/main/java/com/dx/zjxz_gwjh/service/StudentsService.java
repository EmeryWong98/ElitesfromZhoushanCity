package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.StudentsDto;
import com.dx.zjxz_gwjh.entity.*;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.repository.StudentsRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StudentsService extends JpaPublicService<StudentsEntity, String> implements StandardService<StudentsEntity, StudentsFilter, String> {

    @Autowired
    private StudentsRepository repository;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private HighSchoolService highSchoolService;

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
            QUniversityEntity qu = QUniversityEntity.universityEntity;
            QHighSchoolEntity qh = QHighSchoolEntity.highSchoolEntity;


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

            // 学年
            String academicYear = filter.getAcademicYear();
            if (StringUtils.hasText(academicYear)) {
                predicate.and(q.academicYear.contains(academicYear));
            }

            // 属地
            String area = filter.getArea();
            if (StringUtils.hasText(area)) {
                predicate.and(q.area.contains(area));
            }

            // 是否重点学子
            Boolean isKeyContact = filter.getIsKeyContact();
            if (isKeyContact != null) {
                predicate.and(q.isKeyContact.eq(isKeyContact));
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

        // 进一步的后续逻辑，比如如果学生信息更改后需要触发其他操作
        if (entity.getIsKeyContact() != null && entity.getIsKeyContact()) {
            try {
                // 假设有一个特别的逻辑需要在重点学子更新后执行
                // KeyContactEntity keyContact = ObjectUtils.copyEntity(entity, KeyContactEntity.class);
                // keyContactService.create(keyContact);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return entity;
    }

    // 在StudentsService中
    public StudentsEntity createStudent(StudentsDto dto) throws ServiceException {
        // 先检查并获取或创建University实体
        UniversityEntity universityEntity = universityService.findOrCreateByName(dto.getUniversityName());

        // 先检查并获取或创建HighSchool实体
        HighSchoolEntity highSchoolEntity = highSchoolService.findOrCreateByName(dto.getHighSchoolName());

        // 创建或获取现有的学生实体
        StudentsEntity entity;
        if (dto.getId() != null) {
            entity = this.getById(dto.getId());
        } else {
            entity = new StudentsEntity();
        }

        // 将DTO中的数据复制到学生实体
        ObjectUtils.copyEntity(dto, entity);

        // 设置关联的University和HighSchool
        entity.setUniversity(universityEntity);
        entity.setHighSchool(highSchoolEntity);

        // 创建或更新学生实体
        if (dto.getId() != null) {
            return this.update(entity);
        } else {
            return this.create(entity);
        }
    }


}

