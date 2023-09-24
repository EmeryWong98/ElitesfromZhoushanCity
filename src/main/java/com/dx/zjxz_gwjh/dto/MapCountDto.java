package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

import java.util.List;
@Data
@ApiModel("地图统计DTO")
public class MapCountDto {
    private String id;
    private String name;

    private List<ProvinceCountDto> provinceCountList;
}
