package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

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

}
