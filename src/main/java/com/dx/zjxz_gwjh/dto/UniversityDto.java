package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.storage.models.StorageObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("大学")
@EqualsAndHashCode(callSuper = false)
public class UniversityDto extends BaseEventDto{
    @NotNull(message = "大学名称不能为空")
    @ApiModelProperty("大学名称")
    private String name;

    @ApiModelProperty("图标")
    private List<StorageObject> files;

    @ApiModelProperty("经度")
    private float lon;

    @ApiModelProperty("纬度")
    private float lat;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("是否双一流")
    @NotNull(message = "是否双一流不能为空")
    private Boolean isSupreme;

    @ApiModelProperty("重点专业")
    @NotNull(message = "重点专业不能为空")
    private Boolean isKeyMajor;
}
