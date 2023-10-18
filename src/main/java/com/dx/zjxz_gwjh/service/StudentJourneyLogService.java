package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.*;
import com.dx.zjxz_gwjh.entity.QStudentJourneyLogEntity;
import com.dx.zjxz_gwjh.entity.StudentJourneyLogEntity;
import com.dx.zjxz_gwjh.enums.DegreeType;
import com.dx.zjxz_gwjh.filter.StudentJourneyLogEntityFilter;
import com.dx.zjxz_gwjh.repository.StudentJourneyLogRepository;

import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentJourneyLogService extends JpaPublicService<StudentJourneyLogEntity, String> implements StandardService<StudentJourneyLogEntity, StudentJourneyLogEntityFilter, String> {


    private final StudentJourneyLogRepository studentJourneyLogRepository;

    @Autowired
    public StudentJourneyLogService(StudentJourneyLogRepository repository, StudentJourneyLogRepository studentJourneyLogRepository) {
        super(repository);
        this.studentJourneyLogRepository = studentJourneyLogRepository;
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
        studentBackCountDto.setScount(sCount);
        studentBackCountDto.setBcount(bCount);
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
        List<StudentBackAreaCountDto> studentBackAreaCountDtoList = new ArrayList<>();
        List<Object[]> list = studentJourneyLogRepository.countByTimeRangeAndAreaAndIsBack(startYear, endYear + 1);
        AtomicInteger allCount = new AtomicInteger();
        list.forEach(item -> {
            String area = item[0].toString();
            int count = Integer.parseInt(item[1].toString());
            allCount.addAndGet(count);
            studentBackAreaCountDtoList.add(new StudentBackAreaCountDto(count, 0, area));
        });
        studentBackAreaCountDtoList.forEach(item -> item.setRate(getRate(item.getCount(), allCount)));
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
        List<StudentBackYearCountDto> studentBackYearCountDtoList = new ArrayList<>();
        List<Object[]> list = studentJourneyLogRepository.countByTimeRangeAndAcademicYearAndIsBack(startYear, endYear + 1);
        AtomicInteger allCount = new AtomicInteger();
        list.forEach(item -> {
            int year = Integer.parseInt(item[0].toString());
            int count = Integer.parseInt(item[1].toString());
            allCount.addAndGet(count);
            studentBackYearCountDtoList.add(new StudentBackYearCountDto(year, count, 0));
        });
        List<StudentBackYearCountDto> yearList = new ArrayList<>();
        for (int year = studentBackCountQueryDto.getEndYear(); year >= studentBackCountQueryDto.getStartYear(); year--) {
            StudentBackYearCountDto existItem = null;
            for (StudentBackYearCountDto studentBackYearCountDto : studentBackYearCountDtoList) {
                if (studentBackYearCountDto.getYear() == year) {
                    existItem = studentBackYearCountDto;
                    break;
                }
            }
            if (existItem == null) {
                yearList.add(new StudentBackYearCountDto(year, 0, 0));
            } else {
                existItem.setRate(getRate(existItem.getCount(), allCount));
                yearList.add(existItem);
            }
        }

        return yearList;
    }

    private float getRate(int count, AtomicInteger allCount) {
        if (allCount.get() == 0) {
            return 0;
        } else {
            return (float) Math.round((count * 100.0 / allCount.get()) * 100) / 100;
        }
    }

    /**
     * 根据学子id查询学子回舟日志列表
     *
     * @param query 查询条件
     * @return 查询结果
     * @throws ServiceException 业务异常
     */
    @Override
    public PagingData<StudentJourneyLogEntity> queryList(QueryRequest<StudentJourneyLogEntityFilter> query) throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        StudentJourneyLogEntityFilter filter = query.getFilter();
        if (filter == null) {
            throw new ServiceException("查询条件不能为空");
        }
        QStudentJourneyLogEntity q = QStudentJourneyLogEntity.studentJourneyLogEntity;
        //学生ID相等
        String studentId = filter.getId();
        if (studentId != null) {
            predicate.and(q.studentId.eq(studentId));
        }
        Timestamp startTime = filter.getStartTime();
        Timestamp endTime = filter.getEndTime();
        if (startTime != null && endTime != null) {
            predicate.and(q.updateAt.between(startTime, endTime));
        }
        if (query.getSorts() == null) {
            query.setSorts(SortField.by("updateAt", true));
        }
        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    /**
     * 创建学子回舟日志
     *
     * @param studentJourneyLogDto 学子回舟日志
     * @return 学子回舟日志
     * @throws ServiceException 业务异常
     */
    public StudentJourneyLogEntity createStudentJourneyLog(StudentJourneyLogDto studentJourneyLogDto) throws ServiceException {
        StudentJourneyLogEntity studentJourneyLogEntity = new StudentJourneyLogEntity();
        try {
            studentJourneyLogRepository.save(studentJourneyLogEntity);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return studentJourneyLogEntity;
    }

}
