package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("重点专业DTO")
public class EliteMajorDto {
    String universityName;
    int keyContactCount;
    String majorName;
}
