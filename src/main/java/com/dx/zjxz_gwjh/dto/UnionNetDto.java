package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
}
