package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("大学批量上传")
@EqualsAndHashCode(callSuper = false)
public class UniversitiesImportDto {
    @NotNull
    @ApiModelProperty("大学名称")
    private String name;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("是否双一流")
    private Boolean isSupreme;

    @ApiModelProperty("是否重点专业")
    private Boolean isKeyMajor;

}
