package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.storage.models.StorageObject;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("重点大学DTO")
public class EliteUniversityDto {
    String universityName;
    int keyContactCount;
    List<StorageObject> logo;
}
