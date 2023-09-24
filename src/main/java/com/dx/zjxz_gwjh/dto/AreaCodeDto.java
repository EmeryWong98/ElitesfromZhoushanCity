package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;
import java.util.List;

@Data
@ApiModel("区域编码DTO")
public class AreaCodeDto {
    private String id;

    private String name;

    private String level;

    private List<AreaCodeDto> children;
}
