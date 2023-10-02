package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import lombok.Data;

@Data
@ApiModel("学生详情DTO")
public class StudentsInfo {
    private StudentsEntity studentsEntity;
    private String universityName;
    private String degree;
}
