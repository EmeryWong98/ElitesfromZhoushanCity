package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.storage.models.StorageObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@ApiModel("学联网格Dto")
@EqualsAndHashCode(callSuper = false)
public class UnionNetDto extends BaseEventDto{
    @ApiModelProperty("网格名称")
    private String name;
    @ApiModelProperty("网格联系人姓名")
    private String userName;
    @ApiModelProperty("网格联系人电话")
    private String phoneNumber;
    @ApiModelProperty("网格位置")
    private String location;
    @ApiModelProperty("网格活跃度")
    private Float score;

    @ApiModelProperty("经度")
    private Float lon;

    @ApiModelProperty("纬度")
    private Float lat;

    @ApiModelProperty("排序")
    private Integer xorder;

    @ApiModelProperty("图标")
    private List<StorageObject> files;

    @ApiModelProperty("网格状态")
    private Boolean status;
}
