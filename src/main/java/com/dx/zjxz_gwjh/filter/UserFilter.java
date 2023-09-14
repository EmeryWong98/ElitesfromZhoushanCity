package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.easyspringweb.rpc.account.enums.AccountStatus;

import lombok.Getter;

@ApiModel("账号筛选条件")
@Getter
public class UserFilter {
    @ApiModelProperty("账号名")
    private String userName;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("账号状态")
    private AccountStatus status;

    @ApiModelProperty("部门ID")
    private String deptId;

    @ApiModelProperty("角色ID")
    private String roleId;
}
