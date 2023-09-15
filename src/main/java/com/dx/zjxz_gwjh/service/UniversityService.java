package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.entity.QUniversityEntity;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.filter.UniversityFilter;
import com.dx.zjxz_gwjh.repository.UniversityRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;import java.util.Optional;

@Service
public class UniversityService extends JpaPublicService<UniversityEntity, String> implements StandardService<UniversityEntity, UniversityFilter, String> {
    @Autowired
    private UniversityRepository universityRepository;
    public UniversityService(UniversityRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<UniversityEntity> queryList(QueryRequest<UniversityFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        UniversityFilter filter = query.getFilter();
        if (filter != null) {
            QUniversityEntity q = QUniversityEntity.universityEntity;


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (StringUtils.hasText(keyword)) {
                predicate.and(q.name.contains(keyword)
                        .or(q.province.contains(keyword)));
            }
        }

        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public UniversityEntity findOrCreateByName(String name) {
        UniversityEntity universityEntity = universityRepository.findByName(name);
        if (universityEntity == null) {
            // 如果没有找到，创建一个新的大学实体
            universityEntity = new UniversityEntity();
            universityEntity.setName(name);
            // 设置其他需要初始化的字段
            // ...

            // 保存到数据库
            universityRepository.save(universityEntity);
        }
        return universityEntity;
    }




}
