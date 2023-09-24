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

    public HighSchoolEntity findOrCreateByName(String name) {
        HighSchoolEntity highSchoolEntity = highSchoolRepository.findByName(name);
        if (highSchoolEntity == null) {
            // 如果没有找到，创建一个新的高中实体
            highSchoolEntity = new HighSchoolEntity();
            highSchoolEntity.setName(name);
            // 设置其他需要初始化的字段
            // ...

            // 保存到数据库
            highSchoolRepository.save(highSchoolEntity);
        }
        return highSchoolEntity;
    }

    public int count() {
        return (int) highSchoolRepository.count();
    }
}
