package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.easyspringweb.storage.models.StorageObject;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_union_net", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("学联网格")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnionNetEntity extends JpaBaseEntity {

    @ApiModelProperty("网格名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("网格联系人姓名")
    @Column(name = "user_name")
    private String userName;

    @ApiModelProperty("网格联系人ID")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("联系人电话")
    @Column(name = "phone")
    private String phoneNumber;

    @ApiModelProperty("网格位置")
    @Column(name = "location")
    private String location;

    @ApiModelProperty("网格分数")
    @Column(name = "score")
    private Float score;

    @ApiModelProperty("经度")
    @Column(name = "lon")
    private Float lon;

    @ApiModelProperty("纬度")
    @Column(name = "lat")
    private Float lat;

    @ApiModelProperty("排序")
    @Column(name = "x_order")
    private Integer xorder;

    @ApiModelProperty("logo")
    @Column(name = "logo", columnDefinition = "json")
    @Type(type = "json")
    private List<StorageObject> files;

    @ApiModelProperty("网格状态")
    @Column(name = "status")
    private Boolean status;

}
