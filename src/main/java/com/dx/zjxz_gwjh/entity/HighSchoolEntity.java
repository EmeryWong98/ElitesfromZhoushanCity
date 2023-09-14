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
@Table(name = "biz_high_school", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("高中")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HighSchoolEntity extends JpaBaseEntity {
    @ApiModelProperty("高中ID")
    @Column(name = "id", insertable = false, updatable = false)
    private String highSchoolId;

    @ApiModelProperty("高中名称")
    @Column(name = "name")
    private String name;
}
