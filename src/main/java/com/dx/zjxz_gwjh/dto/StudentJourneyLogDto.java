package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.RecordType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel("学生行程日志DTO")
@EqualsAndHashCode(callSuper = false)
public class StudentJourneyLogDto {
    @ApiModelProperty("学生id")
    private String studentId;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("行程日志类型")
    private RecordType recordType;

    @ApiModelProperty("学生属地编码")
    private String areaCode;

    @ApiModelProperty("学生属地")
    private String area;

    @ApiModelProperty("行程日志描述")
    private String logDesc;

    @ApiModelProperty("行程日志时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logTime;
}
