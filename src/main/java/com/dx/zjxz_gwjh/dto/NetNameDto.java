package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.NetType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("网格Dto")
@EqualsAndHashCode(callSuper = false)
public class NetNameDto extends BaseEventDto{
    @ApiModelProperty("网格名称")
    private String name;
    @ApiModelProperty("网格联系人姓名")
    private String userName;
    @ApiModelProperty("网格联系人电话")
    private String phoneNumber;
    @ApiModelProperty("网格种类")
    private NetType type;
    @ApiModelProperty("网格属地")
    private String areaCode;
}
