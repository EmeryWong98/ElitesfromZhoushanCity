package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.entity.DomesticAssistanceEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.QDomesticAssistanceEntity;
import com.dx.zjxz_gwjh.entity.QHighSchoolNetEntity;
import com.dx.zjxz_gwjh.enums.Status;
import com.dx.zjxz_gwjh.filter.DomesticAssistanceFilter;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.DomesticAssistanceRepository;
import com.dx.zjxz_gwjh.vo.ZLBDomesticAssistanceVO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

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

        if (CollectionUtils.isEmpty(query.getSorts())) {
            query.setSorts(SortField.by("updateAt", true));
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public List<ZLBDomesticAssistanceVO> getByIdCard(String idCard) {
        List<DomesticAssistanceEntity> entities = domesticAssistanceRepository.findByIdCard(idCard);
        return entities.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    private ZLBDomesticAssistanceVO convertToVO(DomesticAssistanceEntity entity) {
        ZLBDomesticAssistanceVO vo = new ZLBDomesticAssistanceVO();
        vo.setId(entity.getId());
        vo.setStudentName(entity.getStudentName());
        vo.setPhone(entity.getPhone());
        vo.setIdCard(entity.getIdCard());
        vo.setArea(entity.getArea());
        vo.setContent(entity.getContent());
        vo.setStatus(entity.getStatus());
        return vo;
    }
}
