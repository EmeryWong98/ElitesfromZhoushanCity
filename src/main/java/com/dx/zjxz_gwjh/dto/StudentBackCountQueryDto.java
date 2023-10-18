package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("回舟学子统计查询DTO")
public class StudentBackCountQueryDto {
    private Integer startYear;
    private Integer endYear;
}
