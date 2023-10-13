package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.zjxz_gwjh.enums.DegreeType;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_zlb_students", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("浙里办学子")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZLBStudentsEntity extends JpaBaseEntity {

    @ApiModelProperty("学生姓名")
    @Column(name = "name")
    @NotNull(message = "学生姓名不能为空")
    private String name;

    @ApiModelProperty("身份证号")
    @Column(name = "id_card")
    @NotNull(message = "身份证号不能为空")
    private String idCard;

    @ApiModelProperty("联系电话")
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty("性别")
    @Column(name = "sex")
    private String sex;

    @ApiModelProperty("生日")
    @Column(name = "dob")
    private Date dob;

    @ApiModelProperty("高中")
    @Column(name = "high_school")
    private String highSchool;

    @ApiModelProperty("住址")
    @Column(name = "address")
    private String address;

    @ApiModelProperty("属地")
    @Column(name = "area")
    private String area;

    @ApiModelProperty("学年")
    @Column(name = "academic_year")
    private int academicYear;

    @ApiModelProperty("学历")
    @Column(name = "degree")
    @Enumerated(EnumType.STRING)
    private DegreeType degree;

    @ApiModelProperty("大学名称")
    @Column(name = "university")
    private String university;

    @ApiModelProperty("专业")
    @Column(name = "major")
    private String major;

    @ApiModelProperty("学校省份")
    @Column(name = "university_province")
    private String universityProvince;

    @ApiModelProperty("是否显示")
    @Column(name = "is_show")
    private Boolean isShow;
}
