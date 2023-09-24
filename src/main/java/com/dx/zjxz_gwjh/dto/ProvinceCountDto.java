package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("省份统计DTO")
public class ProvinceCountDto {
    private String name;
    private int count;
    private int schoolCount;
    private String percentage;
    private String rateLevel;
}
