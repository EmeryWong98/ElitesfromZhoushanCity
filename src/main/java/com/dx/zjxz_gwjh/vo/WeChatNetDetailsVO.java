package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("微信端网格员详情VO")
public class WeChatNetDetailsVO {
    @ApiModelProperty("网格员id")
    private String userId;

    @ApiModelProperty("网格员姓名")
    private String userName;

    @ApiModelProperty("网格名称")
    private String name;

    @ApiModelProperty("网格成员总数")
    private String total;
}
