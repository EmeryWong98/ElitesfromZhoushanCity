package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("回舟学子年份统计DTO")
public class StudentBackYearCountDto {

    @ApiModelProperty("回舟学子的届次")
    private Integer year;
    @ApiModelProperty("回舟学子的比例")
    private Float rate;
    @ApiModelProperty("回舟学子的数量")
    private Integer count;

    public StudentBackYearCountDto(int year, int count, float rate) {
        this.count = count;
        this.rate = rate;
        this.year = year;
    }
}
