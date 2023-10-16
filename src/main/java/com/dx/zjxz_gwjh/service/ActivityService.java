package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.JpaCommonRepository;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.easyspringweb.storage.models.StorageObject;
import com.dx.zjxz_gwjh.dto.ActivityCreateDto;
import com.dx.zjxz_gwjh.dto.ActivityStudentQueryDto;
import com.dx.zjxz_gwjh.entity.*;
import com.dx.zjxz_gwjh.filter.ActivityFilter;
import com.dx.zjxz_gwjh.repository.ActivityRepository;
import com.dx.zjxz_gwjh.vo.ActivityDetailVO;
import com.dx.zjxz_gwjh.vo.ActivityStudentVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService extends JpaPublicService<ActivityEntity, String> implements StandardService<ActivityEntity, ActivityFilter, String> {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AreaNetService areaNetService;

    @Autowired
    private HighSchoolNetService highSchoolNetService;

    @Autowired
    private StudentsService studentsService;

    public ActivityService(JpaCommonRepository<ActivityEntity, String> repository) {
        super(repository);
    }

    public PagingData<ActivityDetailVO> getActivityList(QueryRequest<ActivityFilter> query) throws ServiceException {
        ActivityFilter filter = query.getFilter();
        BooleanBuilder predicate = getQueryParams(filter);
        if (query.getSorts() == null) {
            query.setSorts(SortField.by("startTime", true));
        }
        PagingData<ActivityEntity> activityList = this.queryList(predicate, query.getPageInfo(), query.getSorts());
        return getActivityDetailVOPagingData(activityList);
    }

    /**
     * 驾驶舱查询待执行活动列表
     *
     * @param query 查询条件
     * @return 活动列表
     * @throws ServiceException 业务异常
     */
    public PagingData<ActivityDetailVO> getCurrActivityList(QueryRequest<ActivityFilter> query) throws ServiceException {
        ActivityFilter filter = query.getFilter();
        BooleanBuilder predicate = getQueryParams(filter);
        Date now = new Date();
        predicate.and(QActivityEntity.activityEntity.startTime.loe(now));
        predicate.and(QActivityEntity.activityEntity.endTime.goe(now));
        if (query.getSorts() == null) {
            query.setSorts(SortField.by("startTime", true));
        }
        PagingData<ActivityEntity> activityList = this.queryList(predicate, query.getPageInfo(), query.getSorts());
        return getActivityDetailVOPagingData(activityList);
    }


    /**
     * 驾驶舱查询待执行活动列表
     *
     * @param query 查询条件
     * @return 活动列表
     * @throws ServiceException 业务异常
     */
    public PagingData<ActivityDetailVO> getWaitActivityList(QueryRequest<ActivityFilter> query) throws ServiceException {
        ActivityFilter filter = query.getFilter();
        BooleanBuilder predicate = getQueryParams(filter);
        Date now = new Date();
        predicate.and(QActivityEntity.activityEntity.startTime.gt(now));
        predicate.and(QActivityEntity.activityEntity.endTime.gt(now));
        if (query.getSorts() == null) {
            query.setSorts(SortField.by("startTime", true));
        }
        PagingData<ActivityEntity> activityList = this.queryList(predicate, query.getPageInfo(), query.getSorts());
        return getActivityDetailVOPagingData(activityList);
    }

    @NotNull
    private PagingData<ActivityDetailVO> getActivityDetailVOPagingData(PagingData<ActivityEntity> activityList) {
        List<ActivityEntity> activityEntityList = activityList.getData();
        List<ActivityDetailVO> activityDetailVOS = new ArrayList<>();
        activityEntityList.forEach(item -> {
            try {
                activityDetailVOS.add(parseInfo(item));
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        });
        return new PagingData<ActivityDetailVO>(
                activityList.getPageInfo(),
                activityDetailVOS,
                activityList.getSorts()
        );
    }

    /**
     * 查询活动详情
     *
     * @param id 活动id
     * @return 活动实体
     * @throws ServiceException 业务异常
     */
    public ActivityDetailVO getDetail(String id) throws ServiceException {
        ActivityEntity activityEntity = super.getById(id);
        return parseInfo(activityEntity);
    }

    private ActivityDetailVO parseInfo(ActivityEntity activityEntity) throws ServiceException {
        String netName = "";
        String[] participants = activityEntity.getParticipants().split(",");
        try {
            switch (activityEntity.getNetType()) {
                case AREA_NET -> {
                    AreaNetEntity areaNetEntity = areaNetService.getById(activityEntity.getNetId());
                    netName = areaNetEntity.getName();
                }
                case HIGH_SCHOOL_NET -> {
                    HighSchoolNetEntity highSchoolNetEntity = highSchoolNetService.getById(activityEntity.getNetId());
                    netName = highSchoolNetEntity.getName();
                }
                default -> throw new ServiceException("无效的网格类型: " + activityEntity.getNetType());
            }
        } catch (ServiceException e) {
            throw new ServiceException("没有找到对应的网格");
        }

        ActivityDetailVO activityDetailVO = new ActivityDetailVO(activityEntity);
        List<StudentsEntity> students = studentsService.getStudentsByIds(participants);
        String studentNames = students.stream().map(StudentsEntity::getName).collect(Collectors.joining(","));
        activityDetailVO.setNetName(netName);
        activityDetailVO.setParticipants(studentNames);
        return activityDetailVO;
    }

    /**
     * 查询活动列表
     *
     * @param query 查询条件
     * @return 活动列表
     * @throws ServiceException 业务异常
     */
    @Override
    public PagingData<ActivityEntity> queryList(QueryRequest<ActivityFilter> query) throws ServiceException {
        ActivityFilter filter = query.getFilter();
        BooleanBuilder predicate = getQueryParams(filter);
        if (filter.getStartTime() != null) {
            predicate.and(QActivityEntity.activityEntity.startTime.goe(filter.getStartTime()));
        }
        if (filter.getEndTime() != null) {
            predicate.and(QActivityEntity.activityEntity.endTime.loe(filter.getEndTime()));
        }
        if (query.getSorts() == null) {
            query.setSorts(SortField.by("startTime", true));
        }
        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    private BooleanBuilder getQueryParams(ActivityFilter filter) throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        if (filter == null) throw new ServiceException("查询条件不能为空");
        if (filter.getNetId() != null) {
            predicate.and(QActivityEntity.activityEntity.netId.eq(filter.getNetId()));
        }
        if (filter.getUserId() != null) {
            predicate.and(QActivityEntity.activityEntity.userId.eq(filter.getUserId()));
        }
        if (filter.getNetType() != null) {
            predicate.and(QActivityEntity.activityEntity.netType.eq(filter.getNetType()));
        }
        return predicate;
    }

    /**
     * 根据netIds查询学生列表
     *
     * @param activityStudentQueryDto 查询条件
     * @return 学生列表
     * @throws ServiceException 业务异常
     */
    public List<ActivityStudentVO> queryStudentByNetUserId(ActivityStudentQueryDto activityStudentQueryDto) throws ServiceException {
        String userId = activityStudentQueryDto.getUserId();
        String netId = activityStudentQueryDto.getNetId();
        List<String> areaIds = activityRepository.queryAreaNetIdsByUserIdAndNetId(userId, netId);
        List<String> schoolIds = activityRepository.queryHighSchoolNetIdsByUserIdAndNetId(userId, netId);
        List<String> allIds = new ArrayList<>();
        allIds.addAll(areaIds);
        allIds.addAll(schoolIds);
        List<StudentsEntity> studentList = activityRepository.queryStudentByNetIds(allIds);
        List<ActivityStudentVO> activityStudentVOList = new ArrayList<>();
        studentList.forEach(item -> {
            activityStudentVOList.add(new ActivityStudentVO(item.getId(), item.getName()));
        });
        return activityStudentVOList;
    }

    /**
     * 创建活动
     *
     * @param activityCreateDto 活动实体
     * @return 活动实体
     * @throws ServiceException 业务异常
     */
    public ActivityEntity create(ActivityCreateDto activityCreateDto) throws ServiceException {
        Date startTime = activityCreateDto.getStartTime();
        Date endTime = activityCreateDto.getEndTime();
        List<StorageObject> files = activityCreateDto.getFiles();
        List<StorageObject> bannerFiles = activityCreateDto.getBannerFiles();
        if (startTime.compareTo(endTime) > 0) {
            throw new ServiceException("活动开始时间不能大于结束时间");
        }
        if (files.isEmpty()) {
            throw new ServiceException("活动剪影不能为空");
        }
        if (bannerFiles.isEmpty()) {
            throw new ServiceException("活动封面不能为空");
        }
        ActivityEntity entity = super.newEntity(activityCreateDto);
        return super.create(entity);
    }

    /**
     * 更新活动
     *
     * @param entity 活动实体
     * @return 活动实体
     * @throws ServiceException 业务异常
     */
    public ActivityEntity update(ActivityEntity entity) throws ServiceException {
        checkActivityExist(entity.getId());
        return super.update(entity);
    }

    /**
     * 删除活动
     *
     * @param id 活动id
     * @throws ServiceException 业务异常
     */
    @Transactional
    public void deleteById(String id) throws ServiceException {
        // 检查学生是否存在
        checkActivityExist(id);
        super.deleteById(id);
    }

    private void checkActivityExist(String id) throws ServiceException {
        if (!super.existsById(id)) {
            throw new ServiceException("Student with id: " + id + " does not exist");
        }
    }
}
