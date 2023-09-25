package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("静态区域编码DTO")
public class StaticAreaCodeDto {
    private String id;

    private String name;

    private String level;

    private List<StaticAreaCodeDto> children;

    public StaticAreaCodeDto(String id, String name, String level, List<StaticAreaCodeDto> children) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.children = children;
    }

}
