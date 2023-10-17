package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.zjxz_gwjh.enums.RecordType;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_student_journey_log", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("学生回舟记录")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentJourneyLogEntity extends JpaBaseEntity {

    @ApiModelProperty("学生ID")
    @Column(name = "student_id")
    private String studentId;

    @ApiModelProperty("学生姓名")
    @Column(name = "student_name")
    private String studentName;

    @ApiModelProperty("行程日志类型")
    @Enumerated(EnumType.STRING)
    @Column(name = "record_type")
    private RecordType recordType;

    @ApiModelProperty("工作状态")
    @Column(name = "work_condition")
    private Boolean workCondition;

    @ApiModelProperty("工作地点")
    @Column(name = "work_address")
    private String workAddress;
}
