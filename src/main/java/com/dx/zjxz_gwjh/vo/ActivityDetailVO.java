package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.storage.models.StorageObject;
import com.dx.zjxz_gwjh.entity.ActivityEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("活动详情VO")
public class ActivityDetailVO extends ActivityEntity {

    @ApiModelProperty("网格名称")
    private String netName;

    @ApiModelProperty("活动开始时间")
    private Date startTime;

    @ApiModelProperty("活动结束时间")
    private Date endTime;

    @ApiModelProperty("活动名称")
    private String name;

    @ApiModelProperty("活动人员")
    private String participants;

    @ApiModelProperty("活动内容")
    private String content;

    @ApiModelProperty("活动封面图片")
    private List<StorageObject> BannerFiles;

    @ApiModelProperty("活动附件")
    private List<StorageObject> files;

    public ActivityDetailVO(ActivityEntity activityEntity) {
        this.startTime = activityEntity.getStartTime();
        this.endTime = activityEntity.getEndTime();
        this.name = activityEntity.getName();
        this.content = activityEntity.getContent();
        this.setBannerFiles(activityEntity.getBannerFiles());
        this.setFiles(activityEntity.getFiles());
    }
}
