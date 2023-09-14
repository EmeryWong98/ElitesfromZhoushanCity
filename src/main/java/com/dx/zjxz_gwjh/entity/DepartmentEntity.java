package com.dx.zjxz_gwjh.entity;

import javax.persistence.Entity;

import com.dx.easyspringweb.biz.jpa.entity.JpaDepartmentEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class DepartmentEntity extends JpaDepartmentEntity {

}
