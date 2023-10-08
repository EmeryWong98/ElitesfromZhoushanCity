package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.zjxz_gwjh.enums.DegreeType;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_degree_binding", indexes = {
        @Index(columnList = "id"), @Index(columnList = "student_id"), @Index(columnList = "university_id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("学位绑定")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DegreeBindingEntity extends JpaBaseEntity {

    @ApiModelProperty("学生Id")
    @Column(name = "student_id")
    @NotNull(message = "学生ID不能为空")
    private String studentId;

    @ApiModelProperty("大学Id")
    @Column(name = "university_id")
    @NotNull(message = "大学ID不能为空")
    private String universityId;

    @ApiModelProperty("学位")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "学位不能为空")
    private DegreeType degree;

    @ApiModelProperty("专业")
    @Column(name = "major")
    @NotNull(message = "专业不能为空")
    private String major;
}
