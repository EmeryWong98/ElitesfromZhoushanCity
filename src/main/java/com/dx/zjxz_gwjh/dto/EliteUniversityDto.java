package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("重点大学DTO")
public class EliteUniversityDto {
    String universityName;
    int keyContactCount;
}
