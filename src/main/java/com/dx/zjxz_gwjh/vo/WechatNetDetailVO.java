package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("微信端网格详情VO")
public class WechatNetDetailVO {

    public String id;

    public String name;

    public String userName;
}
