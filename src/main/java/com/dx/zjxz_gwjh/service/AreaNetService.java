package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.QAreaNetEntity;
import com.dx.zjxz_gwjh.entity.QHighSchoolNetEntity;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.AreaNetRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaNetService extends JpaPublicService<AreaNetEntity, String> implements StandardService<AreaNetEntity, NetFilter, String> {
    @Autowired
    AreaNetRepository areaNetRepository;

    public AreaNetService(AreaNetRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<AreaNetEntity> queryList(QueryRequest<NetFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        NetFilter filter = query.getFilter();
        if (filter != null) {
            QAreaNetEntity q = QAreaNetEntity.areaNetEntity;


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

    public AreaNetEntity findOrCreateByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        AreaNetEntity areaNetEntity = areaNetRepository.findByName(name);
        if (areaNetEntity == null) {
            areaNetEntity = new AreaNetEntity();
            areaNetEntity.setName(name);
            areaNetEntity = areaNetRepository.save(areaNetEntity);
        }
        return areaNetEntity;

    }

    public AreaNetEntity findOrCreateByNameAndContactorAndPhoneAndAreaCodeAndLocation(String areaNetName, String areaNetContactor, String areaNetContactorMobile, String areaNetAreaCode, String areaNetLocation) {
        if (StringUtils.isBlank(areaNetName)) {
            return null;
        }
        AreaNetEntity areaNetEntity = areaNetRepository.findByName(areaNetName);
        if (areaNetEntity != null) {
            areaNetEntity.setName(areaNetName);
            areaNetEntity.setUserName(areaNetContactor) ;
            areaNetEntity.setPhoneNumber(areaNetContactorMobile);
            areaNetEntity.setAreaCode(areaNetAreaCode);
            areaNetEntity.setLocation(areaNetLocation);
            areaNetEntity = areaNetRepository.save(areaNetEntity);
        } else {
            areaNetEntity = new AreaNetEntity();
            areaNetEntity.setName(areaNetName);
            areaNetEntity.setUserName(areaNetContactor) ;
            areaNetEntity.setPhoneNumber(areaNetContactorMobile);
            areaNetEntity.setAreaCode(areaNetAreaCode);
            areaNetEntity.setLocation(areaNetLocation);
            areaNetEntity = areaNetRepository.save(areaNetEntity);
        }
        areaNetEntity = areaNetRepository.save(areaNetEntity); // 保存或更新实体
        return areaNetEntity;
    }
}
