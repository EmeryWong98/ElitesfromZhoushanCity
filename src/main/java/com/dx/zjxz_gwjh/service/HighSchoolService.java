package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.entity.*;
import com.dx.zjxz_gwjh.filter.HighSchoolFilter;
import com.dx.zjxz_gwjh.repository.HighSchoolRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.querydsl.core.types.OrderSpecifier;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class HighSchoolService extends JpaPublicService<HighSchoolEntity, String> implements StandardService<HighSchoolEntity, HighSchoolFilter, String> {
    @Autowired
    private HighSchoolRepository highSchoolRepository;

    public HighSchoolService(HighSchoolRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<HighSchoolEntity> queryList(QueryRequest<HighSchoolFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        HighSchoolFilter filter = query.getFilter();
        if (filter != null) {
            QHighSchoolEntity q = QHighSchoolEntity.highSchoolEntity;


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (StringUtils.hasText(keyword)) {
                predicate.and(q.name.contains(keyword));
            }
        }


            // 如果没有指定排序，则设置默认排序
        if (CollectionUtils.isEmpty(query.getSorts())) {
            query.setSorts(SortField.by("xorder", false));
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public HighSchoolEntity findByName(String name) throws ServiceException {
        HighSchoolEntity highSchoolEntity = highSchoolRepository.findByName(name);
        if (highSchoolEntity == null) {
            throw new ServiceException("高中不存在，请先添加或修改高中信息");
        }
        return highSchoolEntity;
    }

    public int count() {
        return (int) highSchoolRepository.count();
    }

    public HighSchoolEntity findById(String highSchoolId) throws ServiceException {
        HighSchoolEntity highSchoolEntity = highSchoolRepository.findById(highSchoolId).orElse(null);
        if (highSchoolEntity == null) {
            throw new ServiceException("高中不存在，请先添加或修改高中信息");
        }
        return highSchoolEntity;
    }


    public HighSchoolEntity findOrCreateByName(String highSchoolName) throws ServiceException{
        HighSchoolEntity highSchoolEntity = highSchoolRepository.findByName(highSchoolName.trim());
        if (highSchoolEntity == null) {
            highSchoolEntity = new HighSchoolEntity();
            highSchoolEntity.setName(highSchoolName);
            highSchoolEntity = highSchoolRepository.save(highSchoolEntity);
        }
        return highSchoolEntity;
    }

    public List<HighSchoolEntity> lists() {
        return highSchoolRepository.findAll();
    }
}
