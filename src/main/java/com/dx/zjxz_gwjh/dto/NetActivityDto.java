package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("网格活跃榜DTO")
public class NetActivityDto {

    @NotNull
    private String NetId; // 网格ID
    private String NetName; // 网格名称
    private String NetContactorName; // 网格员名称
    private Float NetScore; // 网格分数

    // 定义一个与参数类型匹配的构造函数
    public NetActivityDto(@NotNull String id, String netName, String contactorName, Float score) {
        this.NetId = id;
        this.NetName = netName;
        this.NetContactorName = contactorName;
        this.NetScore = score;
    }


}
