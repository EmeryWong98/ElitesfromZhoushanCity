package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.RecordType;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

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
public class StudentJourneyLogEntity {
    @ApiModelProperty("ID")
    @Id
    @Column(name = "id")
    private String id;

    @ApiModelProperty("学生ID")
    @Column(name = "student_id")
    private String studentId;

    @ApiModelProperty("学生姓名")
    @Column(name = "student_name")
    private String studentName;

    @ApiModelProperty("回舟类型")
    @Column(name = "type")
    private RecordType type;

    @ApiModelProperty("日志描述")
    @Column(name = "log_desc")
    private String logDesc;

    @ApiModelProperty("日志时间")
    @Column(name = "log_time")
    private Date logTime;
}
