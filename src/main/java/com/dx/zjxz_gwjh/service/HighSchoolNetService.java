package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.HighSchoolNetOverviewDto;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.QHighSchoolNetEntity;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.HighSchoolNetRepository;
import com.dx.zjxz_gwjh.repository.StudentsRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HighSchoolNetService extends JpaPublicService<HighSchoolNetEntity, String> implements StandardService<HighSchoolNetEntity, NetFilter, String> {
    @Autowired
    HighSchoolNetRepository highSchoolNetRepository;

    @Autowired
    private StudentsRepository studentsRepository;

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
        }

        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public HighSchoolNetEntity findOrCreateByName(String name) {
        HighSchoolNetEntity highSchoolNetEntity = highSchoolNetRepository.findByName(name);
        if (highSchoolNetEntity == null) {
            highSchoolNetEntity = new HighSchoolNetEntity();
            highSchoolNetEntity.setName(name);
            highSchoolNetEntity = highSchoolNetRepository.save(highSchoolNetEntity);
        }
        return highSchoolNetEntity;
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
}
