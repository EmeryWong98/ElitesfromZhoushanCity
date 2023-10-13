package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("浙里办学生筛选条件")
public class ZLBStudentsFilter {
    @ApiModelProperty("关键词")
    private String keyword;

    @ApiModelProperty("姓名")
    private String name;
}
