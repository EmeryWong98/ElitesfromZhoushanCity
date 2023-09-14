package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.entity.StudentsEntity;

import javax.validation.constraints.NotNull;

@ApiModel("学子VO")
public class StudentsVO extends StudentsEntity {

    @ApiModelProperty("大学名称")
    public String getUniversityName() {
        return getUniversity() != null ? getUniversity().getName() : null;
    }

    @ApiModelProperty("高中名称")
    public String getHighSchoolName() {
        return getHighSchool() != null ? getHighSchool().getName() : null;
    }

}
