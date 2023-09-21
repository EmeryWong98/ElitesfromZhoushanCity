package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "biz_student_net_binding", indexes = {@Index(columnList = "id")})
@ApiModel("学子网格绑定")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentsNetBindingEntity extends JpaBaseEntity {
    @ApiModelProperty("学生ID")
    @Column(name = "student_id")
    private String studentId;

    @ApiModelProperty("网格ID")
    @Column(name = "net_id")
    private String netId;
}
