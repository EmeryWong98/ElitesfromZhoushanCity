package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("学生DTO")
public class StudentDto {
    private String studentId;
    private String studentName;

    public StudentDto(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }
}
