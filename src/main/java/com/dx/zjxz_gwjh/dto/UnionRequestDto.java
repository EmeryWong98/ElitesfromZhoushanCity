package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.storage.models.StorageObject;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("学联网格请求Dto")
public class UnionRequestDto {
    private String id;
    private String name;
    private Float lon;
    private Float lat;
    private Integer xorder;
    private List<StorageObject> files;
    private Boolean status;
    private Date createTime;


}
