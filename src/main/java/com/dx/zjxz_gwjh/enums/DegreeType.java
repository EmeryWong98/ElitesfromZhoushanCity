package com.dx.zjxz_gwjh.enums;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.core.enums.StandardEnum;
import com.dx.easyspringweb.core.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ApiModel("学历种类")
public enum DegreeType implements StandardEnum<Integer> {
    Undergraduate(0, "全日制本科"),
    Graduate(1, "全日制硕士"),
    PHD(2, "全日制博士");

    private Integer value;
    private String description;

    public static DegreeType fromDescription(String description) throws ServiceException {
        for (DegreeType degreeType : DegreeType.values()) {
            if (degreeType.getDescription().equals(description)) {
                return degreeType;
            }
        }
        throw new ServiceException("无效的学位描述: " + description);
    }
}


