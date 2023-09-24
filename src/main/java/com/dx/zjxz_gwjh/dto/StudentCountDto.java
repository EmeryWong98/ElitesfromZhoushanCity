package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("区域学生统计DTO")
public class StudentCountDto {
    private String id;
    private String name;
    private int count;
}
