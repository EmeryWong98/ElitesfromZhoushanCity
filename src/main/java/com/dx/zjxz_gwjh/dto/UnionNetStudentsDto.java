package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.zjxz_gwjh.vo.StudentDetailsVO;
import com.dx.zjxz_gwjh.vo.StudentsDetailsVO;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@ApiModel("学联网格学生列表Dto")
@EqualsAndHashCode(callSuper = false)
public class UnionNetStudentsDto {
    private String userName;
    private String phoneNumber;
    private String name; // 网格名称
    private int keyStudentsCount; // 重点学子数量
}
