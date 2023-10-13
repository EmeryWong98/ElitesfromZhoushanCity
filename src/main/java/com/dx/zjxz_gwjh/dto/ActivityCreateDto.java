package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.storage.models.StorageObject;
import com.dx.zjxz_gwjh.enums.NetType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("创建活动Dto")
@EqualsAndHashCode(callSuper = false)
public class ActivityCreateDto {
    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private String userId;

    @ApiModelProperty(value = "网格ID")
    @NotNull(message = "网格ID不能为空")
    private String netId;

    @ApiModelProperty(value = "网格类型")
    @NotNull(message = "网格类型不能为空")
    private NetType netType;

    @ApiModelProperty(value = "活动名称")
    @NotNull(message = "活动名称不能为空")
    private String name;

    @ApiModelProperty(value = "活动开始时间")
    @NotNull(message = "活动开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "活动结束时间")
    @NotNull(message = "活动结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "活动参与人员")
    @NotNull(message = "活动参与人员不能为空")
    private String participants;

    @ApiModelProperty(value = "活动内容")
    @NotNull(message = "活动内容不能为空")
    private String content;

    @ApiModelProperty(value = "活动封面图片")
    @NotNull(message = "活动封面图片不能为空")
    private List<StorageObject> BannerFiles;

    @ApiModelProperty(value = "活动剪影图片")
    @NotNull(message = "活动剪影图片不能为空")
    private List<StorageObject> files;
}
