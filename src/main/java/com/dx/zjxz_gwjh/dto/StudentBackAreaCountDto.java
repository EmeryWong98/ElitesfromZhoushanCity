package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("回舟学子地区统计DTO")
public class StudentBackAreaCountDto {
    @ApiModelProperty("已回舟的学子数量")
    private int count;
    @ApiModelProperty("已回舟的学子比例")
    private float rate;
    @ApiModelProperty("地区")
    private String area;

    public StudentBackAreaCountDto(int count, float rate, String area) {
        this.count = count;
        this.rate = rate;
        this.area = area;
    }
}
