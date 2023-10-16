package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.entity.ActivityEntity;
import lombok.Data;

@Data
@ApiModel("活动详情VO")
public class ActivityDetailVO extends ActivityEntity {

    @ApiModelProperty("网格名称")
    private String netName;

    public ActivityDetailVO(ActivityEntity activityEntity, String netName) {
        super(activityEntity);
        this.netName = netName;
    }
}
