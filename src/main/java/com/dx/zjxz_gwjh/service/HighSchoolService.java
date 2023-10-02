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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

            if (query.getSorts() == null) {
                query.setSorts(SortField.def());
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
}
