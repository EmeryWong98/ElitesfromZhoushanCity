package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.zjxz_gwjh.enums.DegreeType;
import com.dx.zjxz_gwjh.enums.Status;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_domestic_assistance", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("家事难事")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomesticAssistanceEntity extends JpaBaseEntity {
    @ApiModelProperty("学生ID")
    @Column(name = "student_id")
    private String studentId;

    @ApiModelProperty("学生姓名")
    @Column(name = "student_name")
    private String studentName;

    @ApiModelProperty("学生电话")
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty("身份证号")
    @Column(name = "id_card")
    private String idCard;

    @ApiModelProperty("属地")
    @Column(name = "area")
    private String area;

    @ApiModelProperty("家事难事")
    @Column(name = "content")
    private String content;

    @ApiModelProperty("状态")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "状态不能为空")
    private Status status;

    @ApiModelProperty("备注")
    @Column(name = "remark")
    private String remark;

}
