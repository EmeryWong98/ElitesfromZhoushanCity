package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.entity.OfficerNetEntity;
import com.dx.zjxz_gwjh.entity.UnionNetEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("网格详情Dto")
@EqualsAndHashCode(callSuper = false)
public class NetDetailsDto {
    private HighSchoolNetEntity highSchoolNet;
    private AreaNetEntity areaNet;
    private OfficerNetEntity officerNet;
    private UnionNetEntity unionNet;
}
