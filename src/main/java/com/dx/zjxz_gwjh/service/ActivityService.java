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
import com.dx.zjxz_gwjh.enums.NetType;
import com.dx.zjxz_gwjh.filter.ActivityFilter;
import com.dx.zjxz_gwjh.repository.ActivityRepository;
import com.dx.zjxz_gwjh.vo.ActivityDetailVO;
import com.dx.zjxz_gwjh.vo.ActivityStudentVO;
import com.dx.zjxz_gwjh.vo.ActivityVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


    /**
     * 驾驶舱查询正在执行活动列表
     *
     * @param netType 网格类型
     * @return 活动列表
     * @throws ServiceException 业务异常
     */
    public List<ActivityVO> getCurrActivityList(NetType netType) throws ServiceException {
        List<ActivityEntity> activityList = activityRepository.queryCurrActivityBy(netType, new Date());
        return parseActivityList(activityList);
    }

    /**
     * 驾驶舱查询待执行活动列表
     *
     * @param netType 网格类型
     * @return 活动列表
     * @throws ServiceException 业务异常
     */
    public List<ActivityVO> getWaitActivityList(NetType netType) throws ServiceException {
        List<ActivityEntity> activityList = activityRepository.queryWaitActivity(netType, new Date());
        return parseActivityList(activityList);
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
        String netName = "";
        String[] participants = activityEntity.getParticipants().split(",");
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
        ActivityDetailVO activityDetailVO = new ActivityDetailVO(activityEntity);
        List<StudentsEntity> students = studentsService.getStudentsByIds(participants);
        String studentNames = students.stream().map(StudentsEntity::getName).collect(Collectors.joining(","));
        activityDetailVO.setNetName(netName);
        activityDetailVO.setParticipants(studentNames);
        return activityDetailVO;
    }

    /**
     * 转换返回列表类型
     *
     * @param activityList 活动列表
     * @return 活动视图列表
     */
    private List<ActivityVO> parseActivityList(List<ActivityEntity> activityList) {
        List<ActivityVO> activityVOList = new ArrayList<>();
        activityList.forEach(item -> {
            ActivityVO activityVO = new ActivityVO();
            activityVO.setName(item.getName());
            activityVO.setBannerFiles(item.getBannerFiles());
            activityVOList.add(activityVO);
        });
        return activityVOList;
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
        BooleanBuilder predicate = new BooleanBuilder();
        ActivityFilter filter = query.getFilter();
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
        if (filter.getStartTime() != null) {
            predicate.and(QActivityEntity.activityEntity.startTime.goe(filter.getStartTime()));
        }
        if (filter.getEndTime() != null) {
            predicate.and(QActivityEntity.activityEntity.endTime.loe(filter.getEndTime()));
        }
        if (query.getSorts() == null) {
            query.setSorts(SortField.by("updateAt", true));
        }
        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
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
