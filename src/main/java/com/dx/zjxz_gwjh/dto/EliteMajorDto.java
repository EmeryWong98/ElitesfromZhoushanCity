package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.storage.models.StorageObject;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("重点专业DTO")
public class EliteMajorDto {
    String universityName;
    int keyContactCount;
    String majorName;
    List<StorageObject> logo;
}
