package com.dx.zjxz_gwjh.model;

import org.apache.commons.lang3.ArrayUtils;

import com.dx.easyspringweb.rpc.account.model.UserSession;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RDUserSession extends UserSession {

    public boolean isGovStaff() {
        return ArrayUtils.contains(this.getRoles(), "乡镇人大工作人员");
    }

    public boolean isAdmin() {
        return ArrayUtils.contains(this.getRoles(), "人大管理员");
    }
}
