package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.storage.models.StorageObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("高中")
@EqualsAndHashCode(callSuper = false)
public class HighSchoolDto extends BaseEventDto{
    @NotNull(message = "高中名称不能为空")
    @ApiModelProperty("高中名称")
    private String name;

    @ApiModelProperty("图标")
    private List<StorageObject> files;

    @ApiModelProperty("经度")
    private float lon;

    @ApiModelProperty("纬度")
    private float lat;

    @ApiModelProperty("是否显示")
    private Boolean isShow;

    @ApiModelProperty("顺序")
    private Integer xorder;
}
