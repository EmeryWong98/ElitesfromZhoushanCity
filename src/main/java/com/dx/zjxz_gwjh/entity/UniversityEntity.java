package com.dx.zjxz_gwjh.entity;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.data.jpa.entity.JpaBaseEntity;
import com.dx.easyspringweb.storage.models.StorageObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
@TypeDef(name = "json", typeClass = JsonType.class)
public class UniversityEntity extends JpaBaseEntity {
    @ApiModelProperty("大学ID")
    @Column(name = "id",insertable = false, updatable = false)
    private String universityId;

    @ApiModelProperty("大学名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("图标")
    @Column(name = "logo", columnDefinition = "json")
    @Type(type = "json")
    private List<StorageObject> files;

    @ApiModelProperty("经度")
    @Column(name = "lon")
    private float lon;

    @ApiModelProperty("纬度")
    @Column(name = "lat")
    private float lat;

    @ApiModelProperty("省份")
    @Column(name = "province")
    private String province;

    @ApiModelProperty("是否双一流")
    @Column(name = "is_supreme")
    private Boolean isSupreme;

    @ApiModelProperty("是否一流专业")
    @Column(name = "is_key_major")
    private Boolean isKeyMajor;

    @ManyToMany(mappedBy = "universities")
    @JsonIgnore
    private Set<StudentsEntity> students;

}
