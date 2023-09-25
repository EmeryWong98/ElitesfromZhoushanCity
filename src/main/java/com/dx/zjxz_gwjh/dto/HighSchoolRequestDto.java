package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("高中请求Dto")
public class HighSchoolRequestDto {
    private String highSchoolId;
    private Integer graduationYear;
    private String netId;
}
