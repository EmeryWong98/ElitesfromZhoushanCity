package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("家事难事筛选条件")
public class DomesticAssistanceFilter {
    @ApiModelProperty("关键词")
    private String keyword;
}
