package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("学生回舟记录筛选条件")
public class StudentJourneyLogEntityFilter {
    @ApiModelProperty("学生姓名")
    private String name;

    @ApiModelProperty("回舟开始时间")
    private Date startTime;

    @ApiModelProperty("回舟结束时间")
    private Date endTime;
}
