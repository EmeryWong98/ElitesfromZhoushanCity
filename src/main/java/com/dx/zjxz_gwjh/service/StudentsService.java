package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.entity.QHighSchoolEntity;
import com.dx.zjxz_gwjh.entity.QStudentsEntity;
import com.dx.zjxz_gwjh.entity.QUniversityEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
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

            // 省份
            String province = filter.getProvince();
            if (StringUtils.hasText(province)) {
                predicate.and(q.province.contains(province));
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
}

