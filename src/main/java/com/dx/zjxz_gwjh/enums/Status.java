package com.dx.zjxz_gwjh.enums;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.core.enums.StandardEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("状态")
public enum Status implements StandardEnum<Integer> {
    Wait(0, "待领取"),
    Processing(1, "办理中"),
    Done(2, "已办结");

    private Integer value;
    private String description;

}
