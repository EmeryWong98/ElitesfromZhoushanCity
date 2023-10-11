package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("学生行程日志VO")
public class StudentJourneyLogVO {
    @ApiModelProperty("日志ID")
    private String id;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("日志描述")
    private String logDesc;

    @ApiModelProperty("日志时间")
    private Date logDate;
}
