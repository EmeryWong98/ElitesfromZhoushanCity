package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("区域请求Dto")
public class AreaRequestDto {
    private String areaId;
    private Integer graduationYear;
    private String netId;
}
