package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.biz.dto.BaseUserDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("账号")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseUserDto<String> {
}
