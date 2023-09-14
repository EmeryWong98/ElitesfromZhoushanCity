package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_students", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("学子")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentsEntity extends JpaBaseEntity{
    @ApiModelProperty("学生ID")
    @Column(name = "id", insertable = false, updatable = false)
    private String studentId;

    @ApiModelProperty("学生姓名")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("专业")
    @Column(name = "major")
    private String major;

    @ApiModelProperty("省份")
    @Column(name = "province")
    private String province;

    @ApiModelProperty("是否重点学子")
    @Column(name = "is_key_contact")
    private Boolean isKeyContact;

    @ApiModelProperty("学年")
    @Column(name = "academic_year")
    private String academicYear;

    @ApiModelProperty("住址")
    @Column(name = "address")
    private String address;

    @ApiModelProperty("高中网格ID")
    @Column(name = "high_school_net_id")
    private String highSchoolNetId;

    @ApiModelProperty("是否回舟")
    @Column(name = "is_back")
    private Boolean isBack;

    @ApiModelProperty("回舟时间")
    @Column(name = "back_time")
    private Date backTime;

    @ApiModelProperty("性别")
    @Column(name = "sex")
    private String sex;

    @ApiModelProperty("身份证号")
    @Column(name = "id_card")
    private String idCard;

    @ApiModelProperty("联系电话")
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty("大学网格ID")
    @Column(name = "university_net_id")
    private String universityNetId;

    @ApiModelProperty("区域网格ID")
    @Column(name = "area_net_id")
    private String areaNetId;

    @ApiModelProperty("生日")
    @Column(name = "dob")
    private Date dob;

    @ManyToOne
    @JoinColumn(name="university_id", referencedColumnName = "id")
    private UniversityEntity university;

    @ManyToOne
    @JoinColumn(name="highSchool_id", referencedColumnName = "id")
    private HighSchoolEntity highSchool;
}
