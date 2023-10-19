package com.dx.zjxz_gwjh.controller.wx;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.Session;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PageInfo;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.UnionNetEntity;
import com.dx.zjxz_gwjh.enums.NetType;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.*;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import com.dx.zjxz_gwjh.vo.WechatNetDetailVO;
import com.dx.zjxz_gwjh.vo.WechatNetStudentVO;
import com.dx.zjxz_gwjh.vo.WechatNetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModule("Net")
@Api(name = "NetWxApi", description = "网格微信API")
@RestController
@RequestMapping("/wx/net")
@BindResource(value = "net:wx", menu = false)
public class NetController {

    @Autowired
    private AreaNetService areaNetService;

    @Autowired
    private HighSchoolNetService highSchoolNetService;

    @Autowired
    private UnionNetService unionNetService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private DegreeBindingService degreeBindingService;


    /**
     * 查询网格列表
     *
     * @param user   用户信息
     * @param userId 用户ID
     * @return 网格列表
     * @throws ServiceException 业务异常
     */
    @BindResource("net:wx:list")
    @Action(value = "网格列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public List<WechatNetVO> queryList(@Session RDUserSession user, @RequestParam(value = "userId", required = false) String userId) throws ServiceException {
        if (userId == null) userId = user.getUserId();
        List<WechatNetVO> wechatNetVOList = new ArrayList<>();
        List<AreaNetEntity> areaNetEntityList = areaNetService.findByUserId(userId);
        areaNetEntityList.forEach(item -> {
            try {
                WechatNetVO vo = ObjectUtils.copyEntity(item, WechatNetVO.class);
                vo.setNetType(NetType.AREA_NET);
                wechatNetVOList.add(vo);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        });
        List<HighSchoolNetEntity> highSchoolNetServiceList = highSchoolNetService.findByUserId(userId);
        highSchoolNetServiceList.forEach(item -> {
            try {
                WechatNetVO vo = ObjectUtils.copyEntity(item, WechatNetVO.class);
                vo.setNetType(NetType.HIGH_SCHOOL_NET);
                wechatNetVOList.add(vo);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        });
        List<UnionNetEntity> unionNetEntityList = unionNetService.findByUserId(userId);
        unionNetEntityList.forEach(item -> {
            try {
                WechatNetVO vo = ObjectUtils.copyEntity(item, WechatNetVO.class);
                vo.setNetType(NetType.UNION_NET);
                wechatNetVOList.add(vo);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        });
        return wechatNetVOList;
    }

    @BindResource("net:wx:detail")
    @Action(value = "网格详情")
    @PostMapping("/detail")
    public WechatNetDetailVO queryNetDetailByNetId(@RequestBody Map<String, Object> request) throws ServiceException {
        WechatNetDetailVO vo = new WechatNetDetailVO();
        String netId = (String) request.get("netId");
        String netType = (String) request.get("netType");
        if(netId == null) throw new ServiceException("网格ID不能为空");
        if(netType == null) throw new ServiceException("网格类型不能为空");
        switch (netType) {
            case "AREA_NET":
                AreaNetEntity netEntity = areaNetService.findById(netId);
                vo.setId(netEntity.getId());
                vo.setName(netEntity.getName());
                vo.setUserName(netEntity.getUserName());
                break;
            case "HIGH_SCHOOL_NET":
                HighSchoolNetEntity highSchoolNetEntity = highSchoolNetService.findById(netId);
                vo.setId(highSchoolNetEntity.getId());
                vo.setName(highSchoolNetEntity.getName());
                vo.setUserName(highSchoolNetEntity.getUserName());
                break;
            case "UNION_NET":
                UnionNetEntity unionNetEntity = unionNetService.findById(netId);
                vo.setId(unionNetEntity.getId());
                vo.setName(unionNetEntity.getName());
                vo.setUserName(unionNetEntity.getUserName());
                break;
        }
        return vo;
    }
    @BindResource("net:wx:student-list")
    @Action(value = "网格学子列表")
    @PostMapping("/studentList")
    public PagingData<StudentsVO> queryStudentByNetId(@RequestBody Map<String, Object> request) throws ServiceException{
        String netId = (String) request.get("netId");
        String netType = (String) request.get("netType");
        if(netId == null) throw new ServiceException("网格ID不能为空");
        if(netType == null) throw new ServiceException("网格类型不能为空");
        QueryRequest<StudentsFilter> queryRequest = new QueryRequest<>();
        StudentsFilter filter = new StudentsFilter();
        switch (netType) {
            case "AREA_NET":
                AreaNetEntity netEntity = areaNetService.findById(netId);
                filter.setAreaNetId(netEntity.getId());
                break;
            case "HIGH_SCHOOL_NET":
                HighSchoolNetEntity highSchoolNetEntity = highSchoolNetService.findById(netId);
                filter.setHighSchoolNetId(highSchoolNetEntity.getId());
                break;
            case "UNION_NET":
                UnionNetEntity unionNetEntity = unionNetService.findById(netId);
                filter.setUnionNetId(unionNetEntity.getId());
                break;
        }
        queryRequest.setFilter(filter);
        PagingData<StudentsEntity> result = studentsService.queryList(queryRequest);
        PagingData<StudentsVO> studentsVOPagingData = result.map((entity) -> {
            StudentsVO studentsVO = ObjectUtils.copyEntity(entity, StudentsVO.class);
            studentsVO.setUniversityName(degreeBindingService.findHighestDegreeUniversityNameByStudentId(entity.getId()));
            studentsVO.setUniversityProvince(degreeBindingService.findHighestDegreeUniversityProvinceByStudentId(entity.getId()));
            studentsVO.setDegree(degreeBindingService.findHighestDegreeByStudentId(entity.getId()));
            studentsVO.setMajor(degreeBindingService.findHighestDegreeMajorByStudentId(entity.getId()));
            return studentsVO;
        });
        return studentsVOPagingData;
    }
}
