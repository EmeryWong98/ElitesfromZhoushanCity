package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.storage.models.StorageObject;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;


@Data
@ApiModel("活动列表VO")
public class ActivityVO {
    @ApiModelProperty("活动ID")
    private String id;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("活动封面图片")
    @Type(type = "json")
    private List<StorageObject> BannerFiles;
}
