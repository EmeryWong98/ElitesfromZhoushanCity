package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.easyspringweb.storage.models.StorageObject;
import com.dx.zjxz_gwjh.enums.NetType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_activities", indexes = {
        @Index(columnList = "id"), @Index(columnList = "user_id"), @Index(columnList = "net_id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("网格活动")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEntity extends JpaBaseEntity {

    @ApiModelProperty("用户ID")
    @Column(name = "user_id")
    @NotNull(message = "用户ID不能为空")
    private String userId;

    @ApiModelProperty("网格ID")
    @Column(name = "net_id")
    @NotNull(message = "网格ID不能为空")
    private String netId;

    @ApiModelProperty("网格类型")
    @Column(name = "net_type")
    @NotNull(message = "网格类型不能为空")
    @Enumerated(EnumType.STRING)
    private NetType netType;

    @ApiModelProperty("活动名称")
    @Column(name = "name")
    @NotNull(message = "活动名称不能为空")
    private String name;

    @ApiModelProperty("活动开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_time")
    @NotNull(message = "活动开始时间不能为空")
    private Date startTime;

    @ApiModelProperty("活动结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_time")
    @NotNull(message = "活动结束时间不能为空")
    private Date endTime;

    @ApiModelProperty("活动参与人员")
    @Column(name = "participants")
    @NotNull(message = "活动参与人员不能为空")
    private String participants;

    @ApiModelProperty("活动内容")
    @Column(name = "content")
    @NotNull(message = "活动内容不能为空")
    private String content;

    @ApiModelProperty("活动封面图片")
    @Type(type = "json")
    @Column(name = "banner_files", columnDefinition = "json")
    @NotNull(message = "活动封面图片不能为空")
    private List<StorageObject> BannerFiles;

    @ApiModelProperty("活动剪影图片")
    @Type(type = "json")
    @Column(name = "files", columnDefinition = "json")
    @NotNull(message = "活动剪影图片不能为空")
    private List<StorageObject> files;
}
