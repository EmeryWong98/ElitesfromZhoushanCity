package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("基础事件DTO")
public class BaseEventDto {
    @ApiModelProperty("ID")
    private String id;
}
