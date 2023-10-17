package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.storage.models.StorageObject;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("高中网格概况Dto")
public class HighSchoolNetOverviewDto {
    private String highSchoolId;
    private String highSchoolName;
    private String areaId;
    private Float lon;
    private Float lat;
    private List<StorageObject> files;
    private long netCount;
    private long studentCount;
    private long eliteStudentCount;
    private int xorder;
}

