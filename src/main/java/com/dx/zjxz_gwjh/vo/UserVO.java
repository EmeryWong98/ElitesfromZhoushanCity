package com.dx.zjxz_gwjh.vo;

import java.util.List;

import com.dx.zjxz_gwjh.entity.UserEntity;
import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.biz.model.Department;
import com.dx.easyspringweb.biz.model.Role;

@ApiModel("人员VO")
public class UserVO extends UserEntity {

    @Override
    public List<Department<String>> getDepts() {
        return null;
    }


}
