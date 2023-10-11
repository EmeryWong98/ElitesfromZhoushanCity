package com.dx.zjxz_gwjh.enums;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("回舟记录类型")
public enum RecordType {
    ENTER(0, "回舟"),
    LEAVE(1, "离舟");

    private final Integer value;
    private final String description;
}
