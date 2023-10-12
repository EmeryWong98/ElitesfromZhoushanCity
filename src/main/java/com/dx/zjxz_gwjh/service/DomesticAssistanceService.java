package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.entity.DomesticAssistanceEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.QDomesticAssistanceEntity;
import com.dx.zjxz_gwjh.entity.QHighSchoolNetEntity;
import com.dx.zjxz_gwjh.filter.DomesticAssistanceFilter;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.DomesticAssistanceRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomesticAssistanceService extends JpaPublicService<DomesticAssistanceEntity, String> implements StandardService<DomesticAssistanceEntity, DomesticAssistanceFilter, String> {

    @Autowired
    private DomesticAssistanceRepository domesticAssistanceRepository;


    public DomesticAssistanceService(DomesticAssistanceRepository repository) {
        super(repository);
    }

    @Override
    public PagingData<DomesticAssistanceEntity> queryList(QueryRequest<DomesticAssistanceFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        DomesticAssistanceFilter filter = query.getFilter();
        if (filter != null) {
            QDomesticAssistanceEntity q = QDomesticAssistanceEntity.domesticAssistanceEntity;


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (org.springframework.util.StringUtils.hasText(keyword)) {
                predicate.and(q.studentName.contains(keyword));
            }
        }

        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }
}
