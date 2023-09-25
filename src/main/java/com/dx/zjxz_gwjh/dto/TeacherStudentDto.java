package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("教师学生DTO")
public class TeacherStudentDto {
    private String teacherName;
    private List<StudentDto> studentList;

    public TeacherStudentDto(String teacherName, List<StudentDto> studentList) {
        this.teacherName = teacherName;
        this.studentList = studentList;
    }
}
