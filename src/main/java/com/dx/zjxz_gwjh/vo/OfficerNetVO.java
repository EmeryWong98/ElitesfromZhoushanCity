package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.parameters.P;

@Data
@ApiModel("党政领导VO")
public class OfficerNetVO {
    @ApiModelProperty("网格ID")
    private String id;
    @ApiModelProperty("领导姓名")
    private String userName;
    @ApiModelProperty("领导职务")
    private String position;
}
