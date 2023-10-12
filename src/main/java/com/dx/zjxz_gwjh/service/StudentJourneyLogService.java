package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.*;
import com.dx.zjxz_gwjh.entity.StudentJourneyLogEntity;
import com.dx.zjxz_gwjh.enums.DegreeType;
import com.dx.zjxz_gwjh.filter.StudentJourneyLogEntityFilter;
import com.dx.zjxz_gwjh.repository.StudentJourneyLogRepository;

import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentJourneyLogService extends JpaPublicService<StudentJourneyLogEntity, String> implements StandardService<StudentJourneyLogEntity, StudentJourneyLogEntityFilter, String> {

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
        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }
        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    /**
     * 查询回舟学子数量
     *
     * @param studentBackCountQueryDto 查询条件
     * @return 回舟学子数量
     * @throws ServiceException 业务异常
     */
    public StudentBackCountDto queryStudentBackCount(StudentBackCountQueryDto studentBackCountQueryDto) throws ServiceException {
        int startYear = studentBackCountQueryDto.getStartYear();
        int endYear = studentBackCountQueryDto.getEndYear();

        if (startYear > endYear) {
            throw new ServiceException("开始时间不能大于结束时间");
        }

        return calcBackCountForDate(startYear, endYear);
    }

    /**
     * 计算回舟学子数量
     *
     * @param startYear 开始年份
     * @param endYear   结束年份
     * @return 回舟学子数量
     */
    private StudentBackCountDto calcBackCountForDate(int startYear, int endYear) {
        //转换startYear为 Date类型
        StudentBackCountDto studentBackCountDto = new StudentBackCountDto();
        int count = studentJourneyLogRepository.CountByTimeRange(startYear, endYear + 1);

        int sCount = studentJourneyLogRepository.CountByTimeRangeAndDegreeAndIsBack(startYear, endYear + 1, DegreeType.Graduate);

        int bCount = studentJourneyLogRepository.CountByTimeRangeAndDegreeAndIsBack(startYear, endYear + 1, DegreeType.PHD);

        studentBackCountDto.setCount(count);
        studentBackCountDto.setSCount(sCount);
        studentBackCountDto.setBCount(bCount);
        return studentBackCountDto;
    }

    /**
     * 查询回舟学子地区数量
     *
     * @param studentBackCountQueryDto 查询条件
     * @return 回舟学子地区数量
     * @throws ServiceException 业务异常
     */
    public List<StudentBackAreaCountDto> queryStudentBackAreaCount(StudentBackCountQueryDto studentBackCountQueryDto) throws ServiceException {
        int startYear = studentBackCountQueryDto.getStartYear();
        int endYear = studentBackCountQueryDto.getEndYear();

        if (startYear > endYear) {
            throw new ServiceException("开始时间不能大于结束时间");
        }
        List<StudentBackAreaCountDto> studentBackAreaCountDtoList = new ArrayList<StudentBackAreaCountDto>();
        List<Object[]> list = studentJourneyLogRepository.countByTimeRangeAndAreaAndIsBack(startYear, endYear + 1);
        AtomicInteger allCount = new AtomicInteger();
        list.forEach(item -> {
            String area = item[0].toString();
            int count = Integer.parseInt(item[1].toString());
            allCount.addAndGet(count);
            studentBackAreaCountDtoList.add(new StudentBackAreaCountDto(count, 0, area));
        });
        studentBackAreaCountDtoList.forEach(item -> {
            item.setRate(getRate(item.getCount(), allCount));
        });
        return studentBackAreaCountDtoList;
    }

    /**
     * 查询回舟学子年份数量
     *
     * @param studentBackCountQueryDto 查询条件
     * @return 回舟学子年份数量
     * @throws ServiceException 业务异常
     */
    public List<StudentBackYearCountDto> queryStudentBackYearCount(StudentBackCountQueryDto studentBackCountQueryDto) throws ServiceException {
        int startYear = studentBackCountQueryDto.getStartYear();
        int endYear = studentBackCountQueryDto.getEndYear();

        if (startYear > endYear) {
            throw new ServiceException("开始时间不能大于结束时间");
        }
        List<StudentBackYearCountDto> studentBackYearCountDtoList = new ArrayList<StudentBackYearCountDto>();
        List<Object[]> list = studentJourneyLogRepository.countByTimeRangeAndAcademicYearAndIsBack(startYear, endYear + 1);
        AtomicInteger allCount = new AtomicInteger();
        list.forEach(item -> {
            int year = Integer.parseInt(item[0].toString());
            int count = Integer.parseInt(item[1].toString());
            allCount.addAndGet(count);
            studentBackYearCountDtoList.add(new StudentBackYearCountDto(year, count, 0));
        });
        studentBackYearCountDtoList.forEach(item -> {
            item.setRate(getRate(item.getCount(), allCount));
        });
        return studentBackYearCountDtoList;
    }

    private float getRate(int count, AtomicInteger allCount) {
        if (allCount.get() == 0) {
            return 0;
        } else {
            return (float) Math.round((count * 100.0 / allCount.get()) * 100) / 100;
        }
    }

    public StudentJourneyLogEntity createStudentJourneyLog(StudentJourneyLogDto studentJourneyLogDto) throws ServiceException {
        StudentJourneyLogEntity studentJourneyLogEntity = new StudentJourneyLogEntity();
        studentJourneyLogRepository.save(studentJourneyLogEntity);
        return studentJourneyLogEntity;
    }


}
