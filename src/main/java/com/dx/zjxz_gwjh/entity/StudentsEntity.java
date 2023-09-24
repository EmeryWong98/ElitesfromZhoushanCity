package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.zjxz_gwjh.dto.NetNameDto;
import com.dx.zjxz_gwjh.enums.EliteType;
import com.dx.zjxz_gwjh.enums.NetType;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

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

    @ApiModelProperty("学生姓名")
    @Column(name = "name")
    @NotNull(message = "学生姓名不能为空")
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
    @NotNull(message = "身份证号不能为空")
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

    @ApiModelProperty("大学名称")
    @Column(name = "university_name")
    private String universityName;

    @ApiModelProperty("大学省份")
    @Column(name = "university_province")
    private String universityProvince;

    @ApiModelProperty("高中名称")
    @Column(name = "high_school_name")
    private String highSchoolName;

    @ApiModelProperty("高中网格ID")
    @Column(name = "high_school_net_id", insertable = false, updatable = false)
    private String highSchoolNetId;

    @ApiModelProperty("高中网格名称")
    @Column(name = "high_school_net_name")
    private String highSchoolNetName;

    @ApiModelProperty("高中网格联系人")
    @Column(name = "high_school_net_contactor")
    private String highSchoolNetContactor;

    @ApiModelProperty("高中网格位置")
    @Column(name = "high_school_net_location")
    private String highSchoolNetLocation;

    @ApiModelProperty("属地网格ID")
    @Column(name = "area_net_id", insertable = false, updatable = false)
    private String areaNetId;

    @ApiModelProperty("属地网格名称")
    @Column(name = "area_net_name")
    private String areaNetName;

    @ApiModelProperty("属地网格联系人")
    @Column(name = "area_net_contactor")
    private String areaNetContactor;

    @ApiModelProperty("属地网格位置")
    @Column(name = "area_net_location")
    private String areaNetLocation;

    @ApiModelProperty("领导网格ID")
    @Column(name = "officer_net_id", insertable = false, updatable = false)
    private String officerNetId;

    @ApiModelProperty("领导名字")
    @Column(name = "officer_net_name")
    private String officerNetName;

    @ApiModelProperty("领导职务")
    @Column(name = "officer_net_title")
    private String officerNetTitle;

    @ApiModelProperty("学联网格ID")
    @Column(name = "union_net_id", insertable = false, updatable = false)
    private String unionNetId;

    @ApiModelProperty("学联网格名称")
    @Column(name = "union_net_name")
    private String unionNetName;

    @ApiModelProperty("学联网格联系人")
    @Column(name = "union_net_contactor")
    private String unionNetContactor;

    @ApiModelProperty("学联网格位置")
    @Column(name = "union_net_location")
    private String unionNetLocation;

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



}
