package com.dx.zjxz_gwjh.service;

import com.dx.easyspringweb.core.StandardService;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.data.jpa.SortField;
import com.dx.easyspringweb.data.jpa.service.JpaPublicService;
import com.dx.zjxz_gwjh.dto.NetNameDto;
import com.dx.zjxz_gwjh.dto.StudentsDto;
import com.dx.zjxz_gwjh.entity.*;
import com.dx.zjxz_gwjh.enums.NetType;
import com.dx.zjxz_gwjh.filter.HighSchoolFilter;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.NetRepository;
import com.dx.zjxz_gwjh.repository.StudentsNetBindingRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class NetService extends JpaPublicService<NetEntity, String> implements StandardService<NetEntity, NetFilter, String> {
    @Autowired
    private NetRepository netRepository;

    @Autowired
    private StudentsNetBindingRepository studentsNetBindingRepository;

    public NetService(NetRepository repository) {
        super(repository);
    }
    @Override
    public PagingData<NetEntity> queryList(QueryRequest<NetFilter> query)
            throws ServiceException {
        BooleanBuilder predicate = new BooleanBuilder();
        NetFilter filter = query.getFilter();
        if (filter != null) {
            QNetEntity q = QNetEntity.netEntity;


            // 关键词搜索
            String keyword = filter.getKeyword();
            if (StringUtils.hasText(keyword)) {
                predicate.and(q.name.contains(keyword));
            }

            // 网格类型
            String type = filter.getType();
            if (StringUtils.hasText(type)) {
                predicate.and(q.type.eq(NetType.valueOf(type)));
            }
        }

        if (query.getSorts() == null) {
            query.setSorts(SortField.def());
        }

        return this.queryList(predicate, query.getPageInfo(), query.getSorts());
    }

    public void createOrUpdateStudentNetRelation(StudentsEntity studentEntity, StudentsDto dto) {
        List<NetNameDto> netNames = dto.getNetNames();
        if(netNames != null) {
            for (NetNameDto netNameDto : netNames) {
                String netName = netNameDto.getName();
                // 查找或创建网格实体
                NetEntity netEntity = netRepository.findByName(netName).orElseGet(() -> {
                    NetEntity newNet = new NetEntity();
                    newNet.setName(netName);
                    newNet.setType(netNameDto.getType());
                    newNet.setUserName(netNameDto.getUserName());
                    newNet.setPhoneNumber(netNameDto.getPhoneNumber());
                    newNet.setAreaCode(netNameDto.getAreaCode());
                    // 设置其他必要的字段
                    return netRepository.save(newNet);
                });

                // 在关联表中创建或更新记录
                StudentsNetBindingEntity bindingEntity = new StudentsNetBindingEntity();
                bindingEntity.setStudentId(studentEntity.getId());
                bindingEntity.setNetId(netEntity.getId());
                // 设置其他必要的字段
                studentsNetBindingRepository.save(bindingEntity);
            }
        }
}

    public int count() {
        return (int) netRepository.count();
    }
}
