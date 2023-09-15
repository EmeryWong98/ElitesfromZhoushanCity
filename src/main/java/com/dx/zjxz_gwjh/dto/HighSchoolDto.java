package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("高中")
@EqualsAndHashCode(callSuper = false)
public class HighSchoolDto extends BaseEventDto{
    @NotNull(message = "高中名称不能为空")
    @ApiModelProperty("高中名称")
    private String name;
}
