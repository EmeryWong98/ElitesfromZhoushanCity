package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("学生VO")
public class StudentVO {
    private String id;
    private String name;
    private String highestDegree;
    private String universityId;

    public StudentVO(String id, String name, String highestDegree, String universityId) {
        this.id = id;
        this.name = name;
        this.highestDegree = highestDegree;
        this.universityId = universityId;
    }

}

