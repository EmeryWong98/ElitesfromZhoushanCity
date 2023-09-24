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
@Table(name = "biz_Officer_net", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("领导网格")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficerNetEntity extends JpaBaseEntity {
    @ApiModelProperty("网格联系人姓名")
    @Column(name = "user_name")
    private String userName;

    @ApiModelProperty("领导ID")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("领导职务")
    @Column(name = "title")
    private String title;
}
