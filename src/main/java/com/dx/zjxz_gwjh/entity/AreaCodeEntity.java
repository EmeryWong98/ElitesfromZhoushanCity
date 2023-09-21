package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Data
@Entity
@Proxy(lazy = false)
@Table(name = "biz_areacodes", indexes = {
        @Index(columnList = "id")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("区域编码")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaCodeEntity {
    @ApiModelProperty("ID")
    @Id
    @Column(name = "id")
    private String id;

    @ApiModelProperty("区域名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("区域编码")
    @Column(name = "code")
    private String code;

    @ApiModelProperty("父级编码")
    @Column(name = "parent_code")
    private String parentCode;

    @ApiModelProperty("区域等级")
    @Column(name = "level")
    private String level;
}
