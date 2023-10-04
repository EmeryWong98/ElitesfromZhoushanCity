package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("学联网格VO")
public class UnionNetVO {
    @ApiModelProperty("网格ID")
    private String id;

    @ApiModelProperty("网格名称")
    private String name;

    @ApiModelProperty("网格联系人姓名")
    private String userName;

    @ApiModelProperty("联系人电话")
    private String phoneNumber;

    @ApiModelProperty("网格位置")
    private String location;

    @ApiModelProperty("活跃度")
    private Float score;

    @ApiModelProperty("经度")
    private Float lon;

    @ApiModelProperty("纬度")
    private Float lat;


}
