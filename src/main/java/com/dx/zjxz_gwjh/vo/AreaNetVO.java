package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import lombok.Data;

@Data
@ApiModel("属地网格VO")
public class AreaNetVO extends AreaNetEntity {
    @ApiModelProperty("网格ID")
    private String id;

    @ApiModelProperty("网格名称")
    private String name;

    @ApiModelProperty("网格联系人ID")
    private String userId;

    @ApiModelProperty("网格联系人姓名")
    private String userName;

    @ApiModelProperty("联系人电话")
    private String phoneNumber;

    @ApiModelProperty("网格属地")
    private String areaCode;

    @ApiModelProperty("网格位置")
    private String location;

    @ApiModelProperty("活跃度")
    private Float score;
}
