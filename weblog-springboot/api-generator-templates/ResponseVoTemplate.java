package {{packageName}}.model.vo.{{entityNameLowerFirst}};

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: {{entityName}}分页响应 VO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询{{entityName}}分页响应 VO")
public class Find{{entityName}}PageListRspVO {

    @ApiModelProperty(value = "ID")
    private Long id;

    {{#each fields}}
    @ApiModelProperty(value = "{{this.description}}")
    private {{this.type}} {{this.name}};
    {{/each}}

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: {{entityName}}详情响应 VO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "{{entityName}}详情响应 VO")
public class Find{{entityName}}DetailRspVO {

    @ApiModelProperty(value = "ID")
    private Long id;

    {{#each fields}}
    @ApiModelProperty(value = "{{this.description}}")
    private {{this.type}} {{this.name}};
    {{/each}}

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}