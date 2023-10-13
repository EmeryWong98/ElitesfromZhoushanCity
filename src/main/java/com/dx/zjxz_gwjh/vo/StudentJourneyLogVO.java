package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.RecordType;
import lombok.Data;

@Data
@ApiModel("学生行程日志VO")
public class StudentJourneyLogVO {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("学生ID")
    private String studentId;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("行程日志类型")
    private RecordType recordType;

    @ApiModelProperty("工作状态")
    private Boolean workCondition;

    @ApiModelProperty("工作地点")
    private String workAddress;
}
