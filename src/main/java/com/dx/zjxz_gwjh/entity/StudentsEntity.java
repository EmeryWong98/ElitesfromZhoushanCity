package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_students", indexes = {
        @Index(columnList = "id"), @Index(columnList = "area"), @Index(columnList = "high_school_id"), @Index(columnList = "high_school_net_id"), @Index(columnList = "area_net_id"), @Index(columnList = "officer_net_id"), @Index(columnList = "union_net_id"), @Index(columnList = "academic_year"),  @Index(columnList = "is_key_contact")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("学子")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentsEntity extends JpaBaseEntity{

    @ApiModelProperty("学生姓名")
    @Column(name = "name")
    @NotNull(message = "学生姓名不能为空")
    private String name;

    @ApiModelProperty("用户ID")
    @Column(name = "user_id")
    private String userId;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date backTime;

    @ApiModelProperty("性别")
    @Column(name = "sex")
    private String sex;

    @ApiModelProperty("身份证号")
    @Column(name = "id_card")
    @NotNull(message = "身份证号不能为空")
    private String idCard;

    @ApiModelProperty("联系电话")
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty("生日")
    @Column(name = "dob")
    private Date dob;

    @ManyToOne
    @JoinColumn(name="high_School_id", referencedColumnName = "id")
    private HighSchoolEntity highSchool;

    @OneToMany
    @JoinColumn(name="student_id", referencedColumnName = "id")
    private Set<DegreeBindingEntity> degreeBindings;

    @ApiModelProperty("省份")
    @Column(name = "province")
    private String province;

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

    @ApiModelProperty("是否双一流")
    @Column(name = "is_supreme")
    private Boolean isSupreme;

    @ApiModelProperty("高中ID")
    @Column(name = "high_school_id", insertable = false, updatable = false)
    private String highSchoolId;

    @ApiModelProperty("高中网格ID")
    @Column(name = "high_school_net_id", insertable = false, updatable = false)
    private String highSchoolNetId;

    @ApiModelProperty("属地网格ID")
    @Column(name = "area_net_id", insertable = false, updatable = false)
    private String areaNetId;

    @ApiModelProperty("领导网格ID")
    @Column(name = "officer_net_id", insertable = false, updatable = false)
    private String officerNetId;

    @ApiModelProperty("学联网格ID")
    @Column(name = "union_net_id", insertable = false, updatable = false)
    private String unionNetId;

    @ManyToOne
    @JoinColumn(name="high_school_net_id", referencedColumnName = "id")
    private HighSchoolNetEntity highSchoolNet;

    @ManyToOne
    @JoinColumn(name="area_net_id", referencedColumnName = "id")
    private AreaNetEntity areaNet;

    @ManyToOne
    @JoinColumn(name="officer_net_id", referencedColumnName = "id")
    private OfficerNetEntity officerNet;

    @ManyToOne
    @JoinColumn(name="union_net_id", referencedColumnName = "id")
    private UnionNetEntity unionNet;

    @ManyToMany
    @JoinTable(
            name = "biz_degree_binding",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "university_id"))
    @JsonIgnore
    private Set<UniversityEntity> universities;

}
