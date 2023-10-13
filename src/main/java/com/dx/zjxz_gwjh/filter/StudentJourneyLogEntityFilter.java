package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ApiModel("学生回舟记录筛选条件")
public class StudentJourneyLogEntityFilter {
    @ApiModelProperty("学生ID")
    private String id;

    @ApiModelProperty("回舟开始时间")
    private Timestamp startTime;

    @ApiModelProperty("回舟结束时间")
    private Timestamp endTime;
}
