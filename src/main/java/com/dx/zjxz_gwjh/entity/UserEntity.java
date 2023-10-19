package com.dx.zjxz_gwjh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.biz.jpa.entity.JpaUserEntity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends JpaUserEntity {

    @Column(name = "township")
    @ApiModelProperty("所属区域")
    private String township;

    @Column(name = "wechat_id")
    @ApiModelProperty("微信ID")
    private String wechatId;

}
