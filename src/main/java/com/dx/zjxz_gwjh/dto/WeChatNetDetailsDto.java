package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("微信端网格员详情Dto")
@EqualsAndHashCode(callSuper = false)
public class WeChatNetDetailsDto {
    @ApiModelProperty("网格员id")
    private String userId;

    @ApiModelProperty("网格员姓名")
    private String userName;

    @ApiModelProperty("网格员电话")
    private String phone;

    @ApiModelProperty("网格成员总数")
    private String total;

}
