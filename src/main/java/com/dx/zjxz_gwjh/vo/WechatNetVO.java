package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.NetType;
import lombok.Data;

@Data
@ApiModel("微信端网格VO")
public class WechatNetVO {

    @ApiModelProperty("网格ID")
    private String id;

    @ApiModelProperty("网格名称")
    private String name;

    @ApiModelProperty("网格联系号码")
    private String phoneNumber;

    @ApiModelProperty("网格负责人ID")
    private String userId;

    @ApiModelProperty("网格负责人姓名")
    private String userName;

    @ApiModelProperty("网格类型")
    private NetType netType;

}
