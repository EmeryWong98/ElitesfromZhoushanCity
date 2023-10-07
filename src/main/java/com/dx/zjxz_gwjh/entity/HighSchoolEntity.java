package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.easyspringweb.storage.models.StorageObject;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.List;

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
@TypeDef(name = "json", typeClass = JsonType.class)
public class HighSchoolEntity extends JpaBaseEntity {

    @ApiModelProperty("高中名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("图标")
    @Column(name = "logo", columnDefinition = "json")
    @Type(type = "json")
    private List<StorageObject> files;

    @ApiModelProperty("lon")
    @Column(name = "lon")
    private float lon;

    @ApiModelProperty("lat")
    @Column(name = "lat")
    private float lat;


}
