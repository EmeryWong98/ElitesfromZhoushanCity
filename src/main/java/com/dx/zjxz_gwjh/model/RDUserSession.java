package com.dx.zjxz_gwjh.model;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RDUserSession extends com.dx.easyspringweb.rpc.account.model.UserSession {

    private String township;

    public boolean isStaff() {
        return ArrayUtils.contains(this.getRoles(), "区域工作人员");
    }

}
