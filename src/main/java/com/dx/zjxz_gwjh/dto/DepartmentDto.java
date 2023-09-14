package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.biz.model.Department;

import lombok.Data;

@Data
public class DepartmentDto implements Department<String> {
    private String id;

    private String name;

    private String parentId;

    private String parentName;

    private String description;

    private Integer order;

    private String code;
}
