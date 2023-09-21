package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.zjxz_gwjh.enums.NetType;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_net", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("网格")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetEntity extends JpaBaseEntity {
    @ApiModelProperty("网格名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("网格联系人姓名")
    @Column(name = "user_name")
    private String userName;

    @ApiModelProperty("网格联系人ID")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("种类")
    @Enumerated(EnumType.STRING)
    private NetType type;

    @ApiModelProperty("联系人电话")
    @Column(name = "phone_number")
    private String phoneNumber;

    @ApiModelProperty("网格属地")
    @Column(name = "area_code")
    private String areaCode;



}
