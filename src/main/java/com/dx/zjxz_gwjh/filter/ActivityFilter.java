package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.NetType;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("活动筛选条件")
public class ActivityFilter {
    @ApiModelProperty("网格ID")
    private String netId;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("网格类型")
    private NetType netType;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("活动开始时间")
    private Date startTime;

    @ApiModelProperty("活动结束时间")
    private Date endTime;
}
