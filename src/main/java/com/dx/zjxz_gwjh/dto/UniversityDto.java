package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("大学")
@EqualsAndHashCode(callSuper = false)
public class UniversityDto extends BaseEventDto{
    @NotNull(message = "大学名称不能为空")
    @ApiModelProperty("大学名称")
    private String name;

    @ApiModelProperty("图标")
    private String logo;

    @ApiModelProperty("经度")
    private float lon;

    @ApiModelProperty("纬度")
    private float lat;

    @ApiModelProperty("省份")
    private String province;
}
