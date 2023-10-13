package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("回舟学子年份统计DTO")
public class StudentBackYearCountDto {

    @ApiModelProperty("回舟学子的届次")
    private int year;
    @ApiModelProperty("回舟学子的比例")
    private float rate;
    @ApiModelProperty("回舟学子的数量")
    private int count;

    public StudentBackYearCountDto(int year, int count, float rate) {
        this.count = count;
        this.rate = rate;
        this.year = year;
    }
}
