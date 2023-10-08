package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("学生DTO")
public class StudentDto {
    private String studentId;
    private String studentName;
    private String studentSex;

    public StudentDto(String studentId, String studentName, String studentSex) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentSex = studentSex;
    }
}
