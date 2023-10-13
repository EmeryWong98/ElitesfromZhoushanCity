package com.dx.zjxz_gwjh.enums;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.core.enums.StandardEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("舟籍学子审核状态")
public enum ZLBStatus implements StandardEnum<Integer> {
    Fail(0, "审核失败"),
    Processing(1, "审核中"),
    Succeed(2, "审核成功");

    private Integer value;
    private String description;

}
