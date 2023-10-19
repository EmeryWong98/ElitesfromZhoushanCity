package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.NetType;
import lombok.Data;

@Data
@ApiModel("微信端学生筛选条件")
public class WechatStudentsFilter {

    @ApiModelProperty("网格ID")
    private String netId;

    @ApiModelProperty("网格类型")
    private NetType netType;
}
