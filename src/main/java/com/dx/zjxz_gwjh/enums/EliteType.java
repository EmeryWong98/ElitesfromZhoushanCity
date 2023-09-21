package com.dx.zjxz_gwjh.enums;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.core.enums.StandardEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("重点种类")
public enum EliteType implements StandardEnum<Integer> {
    NOT(0, "否"),
    YES(1, "是"),
    SUPREME(2, "是（双一流）");

    private Integer value;
    private String description;
}
