package com.dx.zjxz_gwjh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.biz.jpa.entity.JpaUserEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends JpaUserEntity {

    @ApiModelProperty("所属区域")
    private String township;

}
