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
@Table(name = "biz_university", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("大学")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityEntity extends JpaBaseEntity {
    @ApiModelProperty("大学ID")
    @Column(name = "id",insertable = false, updatable = false)
    private String universityId;

    @ApiModelProperty("大学名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("图标")
    @Column(name = "logo")
    private String logo;

    @ApiModelProperty("经度")
    @Column(name = "lon")
    private float lon;

    @ApiModelProperty("纬度")
    @Column(name = "lat")
    private float lat;

    @ApiModelProperty("省份")
    @Column(name = "province")
    private String province;
}
