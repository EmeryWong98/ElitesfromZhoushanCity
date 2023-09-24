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
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.repository.*;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class NetService {

    @Autowired
    private HighSchoolNetRepository highSchoolNetRepository;

    @Autowired
    private AreaNetRepository areaNetRepository;

    @Autowired
    private OfficerNetRepository officerNetRepository;

    @Autowired
    private UnionNetRepository unionNetRepository;

    public int count() {
        int highSchoolNetCount = (int) highSchoolNetRepository.count();
        int areaNetCount = (int) areaNetRepository.count();
        int officerNetCount = (int) officerNetRepository.count();
        int unionNetCount = (int) unionNetRepository.count();

        return highSchoolNetCount + areaNetCount + officerNetCount + unionNetCount;
    }
}
