package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("重点大学列表DTO")
public class EliteUniversityListDto {
    List<EliteUniversityDto> eliteUniversities;
    List<EliteMajorDto> eliteMajors;
}
