package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.StudentBackCountDto;
import com.dx.zjxz_gwjh.dto.StudentBackCountQueryDto;
import com.dx.zjxz_gwjh.dto.StudentJourneyLogDto;
import com.dx.zjxz_gwjh.entity.StudentJourneyLogEntity;
import com.dx.zjxz_gwjh.filter.StudentJourneyLogEntityFilter;
import com.dx.zjxz_gwjh.repository.StudentJourneyLogRepository;
import com.dx.zjxz_gwjh.repository.StudentsRepository;
import com.dx.zjxz_gwjh.util.Format;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudentJourneyLogService extends JpaPublicService<StudentJourneyLogEntity, String> implements StandardService<StudentJourneyLogEntity, StudentJourneyLogEntityFilter, String> {

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private StudentJourneyLogRepository studentJourneyLogRepository;

    public StudentJourneyLogService(StudentJourneyLogRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<StudentJourneyLogEntity> queryList(QueryRequest<StudentJourneyLogEntityFilter> query) throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        StudentJourneyLogEntityFilter filter = query.getFilter();
        if (filter == null) {
            throw new ServiceException("查询条件不能为空");
        }
        Date startTime = filter.getEndTime();
        Date endTime = filter.getEndTime();
        if (startTime.compareTo(endTime) > 0) {
            throw new ServiceException("开始时间不能大于结束时间");
        }
        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }
        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public StudentBackCountDto queryStudentBackCount(StudentBackCountQueryDto studentBackCountQueryDto) throws ServiceException {
        int startYear = studentBackCountQueryDto.getStartYear();
        int endYear = studentBackCountQueryDto.getEndYear();

        if (startYear > endYear) {
            throw new ServiceException("开始时间不能大于结束时间");
        }

        return calcBackCountForDate(startYear, endYear);
    }

    public StudentJourneyLogEntity createStudentJourneyLog(StudentJourneyLogDto studentJourneyLogDto) throws ServiceException {
        StudentJourneyLogEntity studentJourneyLogEntity = new StudentJourneyLogEntity();
        studentJourneyLogRepository.save(studentJourneyLogEntity);
        return studentJourneyLogEntity;
    }

    private StudentBackCountDto calcBackCountForDate(int startYear, int endYear) {
        //转换startYear为 Date类型
        Date sTime = new Format().YearStringToYearDate(startYear);
        Date eTime = new Format().YearStringToYearDate(endYear + 1);
        StudentBackCountDto studentBackCountDto = new StudentBackCountDto();
        int count = studentJourneyLogRepository.countByTimeRange(sTime, eTime);
        studentBackCountDto.setCount(count);
        //后面调用学生类的方法
        studentBackCountDto.setSCount(0);
        studentBackCountDto.setBCount(0);
        return studentBackCountDto;
    }
}
