package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("重点学生统计DTO")
public class EliteCountDto {
    private String id;
    private String name;
    private int count;
    private int scount;
    private int bcount;
}
