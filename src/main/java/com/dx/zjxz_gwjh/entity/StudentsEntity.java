package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.zjxz_gwjh.enums.EliteType;
import com.dx.zjxz_gwjh.enums.NetType;
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

    @ApiModelProperty("用户ID")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("专业")
    @Column(name = "major")
    private String major;

    @ApiModelProperty("属地")
    @Column(name = "area")
    private String area;

    @ApiModelProperty("是否重点学子")
    @Column(name = "is_key_contact")
    private Boolean isKeyContact;

    @ApiModelProperty("学年")
    @Column(name = "academic_year")
    private int academicYear;

    @ApiModelProperty("住址")
    @Column(name = "address")
    private String address;

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

    @ApiModelProperty("生日")
    @Column(name = "dob")
    private Date dob;

    @ManyToOne
    @JoinColumn(name="university_id", referencedColumnName = "id")
    private UniversityEntity university;

    @ManyToOne
    @JoinColumn(name="highSchool_id", referencedColumnName = "id")
    private HighSchoolEntity highSchool;

    @ApiModelProperty("省份")
    @Column(name = "province")
    private String province;

    @ApiModelProperty("学历")
    @Column(name = "degree")
    private String degree;

    @ApiModelProperty("家庭联系人")
    @Column(name = "family_contactor")
    private String familyContactor;

    @ApiModelProperty("家庭联系人电话")
    @Column(name = "family_contactor_mobile")
    private String familyContactorMobile;

    @ApiModelProperty("所属毕业班")
    @Column(name = "undergraduate_class")
    private String undergraduateClass;

    @ApiModelProperty("联系老师")
    @Column(name = "head_teacher")
    private String headTeacher;

    @ApiModelProperty("联系老师电话")
    @Column(name = "head_teacher_mobile")
    private String headTeacherMobile;

    @ApiModelProperty("大学ID")
    @Column(name = "university_id", insertable = false, updatable = false)
    private String universityId;

    @ApiModelProperty("是否双一流")
    @Column(name = "is_supreme")
    private Boolean isSupreme;
}
