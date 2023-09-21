package com.dx.zjxz_gwjh.enums;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.core.enums.StandardEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("网格种类")
public enum NetType implements StandardEnum<Integer> {
    HIGH_SCHOOL_NET(0, "毕业中学网格"),
    AREA_NET(1, "家庭属地网格"),
    OFFICER_NET(2, "党政干部网格");

    private Integer value;
    private String description;
}
