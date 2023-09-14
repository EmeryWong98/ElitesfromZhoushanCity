package com.dx.zjxz_gwjh.service;

import org.springframework.stereotype.Service;

import com.dx.zjxz_gwjh.entity.DepartmentEntity;
import com.dx.zjxz_gwjh.filter.DepartmentFilter;
import com.dx.zjxz_gwjh.repository.DepartmentRepository;
import com.dx.easyspringweb.biz.jpa.service.JpaBaseDepartmentService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Service
public class DepartmentService extends JpaBaseDepartmentService<DepartmentEntity, DepartmentFilter, String> {

    public DepartmentService(DepartmentRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<DepartmentEntity> queryList(QueryRequest<DepartmentFilter> query) throws ServiceException {
        Predicate predicate = new BooleanBuilder();
        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }
}
