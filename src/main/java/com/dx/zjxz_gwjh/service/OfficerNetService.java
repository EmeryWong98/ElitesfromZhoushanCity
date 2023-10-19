package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.entity.OfficerNetEntity;
import com.dx.zjxz_gwjh.entity.QAreaNetEntity;
import com.dx.zjxz_gwjh.entity.QOfficerNetEntity;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.AreaNetRepository;
import com.dx.zjxz_gwjh.repository.OfficerNetRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class OfficerNetService extends JpaPublicService<OfficerNetEntity, String> implements StandardService<OfficerNetEntity, NetFilter, String> {
    @Autowired
    OfficerNetRepository officerNetRepository;

    public OfficerNetService(OfficerNetRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<OfficerNetEntity> queryList(QueryRequest<NetFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        NetFilter filter = query.getFilter();
        if (filter != null) {
            QOfficerNetEntity q = QOfficerNetEntity.officerNetEntity;


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (org.springframework.util.StringUtils.hasText(keyword)) {
                predicate.and(q.userName.contains(keyword));
            }
        }

        if (CollectionUtils.isEmpty(query.getSorts())) {
            query.setSorts(SortField.by("updateAt", true));
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public OfficerNetEntity findByName(String officerNetName) throws ServiceException{
        if (StringUtils.isBlank(officerNetName)) {
            return null;
        }

        OfficerNetEntity officerNetEntity = officerNetRepository.findByUserName(officerNetName);
        if (officerNetEntity == null) {
            throw new ServiceException("该党政领导不存在，请先添加或修改党政领导信息");
        }
        return officerNetEntity;
    }

    public String findNameById(String officerNetId) {
        if (StringUtils.isBlank(officerNetId)) {
            return null;
        }

        OfficerNetEntity officerNetEntity = officerNetRepository.findById(officerNetId).orElse(null);
        if (officerNetEntity == null) {
            return null;
        }
        return officerNetEntity.getUserName();
    }

    public OfficerNetEntity findById(String officerNetId) throws ServiceException{
        if (StringUtils.isBlank(officerNetId)) {
            return null;
        }

        OfficerNetEntity officerNetEntity = officerNetRepository.findById(officerNetId).orElse(null);
        if (officerNetEntity == null) {
            throw new ServiceException("该党政领导不存在，请先添加或修改党政领导信息");
        }
        return officerNetEntity;
    }

    public OfficerNetEntity findOrCreateByNameAndTitle(String officerNetName, String officerNetPosition) {
        if (StringUtils.isBlank(officerNetName)) {
            return null;
        }
        OfficerNetEntity officerNetEntity = officerNetRepository.findByUserName(officerNetName);
        if (officerNetEntity != null) {
            officerNetEntity.setUserName(officerNetName);
            officerNetEntity.setTitle(officerNetPosition);
            officerNetEntity = officerNetRepository.save(officerNetEntity);
        } else {
            officerNetEntity = new OfficerNetEntity();
            officerNetEntity.setUserName(officerNetName);
            officerNetEntity.setTitle(officerNetPosition);
            officerNetEntity = officerNetRepository.save(officerNetEntity);
        }
        officerNetEntity = officerNetRepository.save(officerNetEntity); // 保存或更新实体
        return officerNetEntity;
    }

    public List<OfficerNetEntity> lists() {
        return officerNetRepository.findAll();
    }
}
