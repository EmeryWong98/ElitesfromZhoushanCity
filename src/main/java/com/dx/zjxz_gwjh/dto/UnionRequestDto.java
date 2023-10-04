package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("学联网格请求Dto")
public class UnionRequestDto {
    private String id;
    private String name;
    private Float lon;
    private Float lat;


}
