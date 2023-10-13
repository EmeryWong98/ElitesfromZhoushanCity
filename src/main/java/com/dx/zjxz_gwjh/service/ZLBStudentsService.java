package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.entity.*;
import com.dx.zjxz_gwjh.filter.HighSchoolFilter;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.filter.ZLBStudentsFilter;
import com.dx.zjxz_gwjh.repository.HighSchoolRepository;
import com.dx.zjxz_gwjh.repository.ZLBStudentsRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ZLBStudentsService extends JpaPublicService<ZLBStudentsEntity, String> implements StandardService<ZLBStudentsEntity, ZLBStudentsFilter, String> {

    @Autowired
    private ZLBStudentsRepository zlbStudentsRepository;

    public ZLBStudentsService(ZLBStudentsRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<ZLBStudentsEntity> queryList(QueryRequest<ZLBStudentsFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        ZLBStudentsFilter filter = query.getFilter();
        if (filter != null) {
            QZLBStudentsEntity q = QZLBStudentsEntity.zLBStudentsEntity;


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


    public ZLBStudentsEntity getByIdCard(String idCard) {
        return zlbStudentsRepository.getByIdCard(idCard);
    }
}
