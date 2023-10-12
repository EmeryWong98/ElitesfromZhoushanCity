package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("区域编码DTO")
public class SimpleAreaCodeDto {
    private String id;

    private String name;

    public SimpleAreaCodeDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
