package {{packageName}}.model.vo.{{entityNameLowerFirst}};

import {{commonPackage}}.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: 创建{{entityName}} VO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "创建{{entityName}} VO")
public class Create{{entityName}}ReqVO {

    {{#each fields}}
    @ApiModelProperty(value = "{{this.description}}")
    {{#if this.required}}
    @NotBlank(message = "{{this.description}}不能为空")
    {{#unless this.noValidation}}
    @Length(min = {{this.minLength}}, max = {{this.maxLength}}, message = "{{this.description}}长度需在 {{this.minLength}} 到 {{this.maxLength}} 之间")
    {{/unless}}
    {{/if}}
    private {{this.type}} {{this.name}};
    {{/each}}
}

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: 更新{{entityName}} VO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "更新{{entityName}} VO")
public class Update{{entityName}}ReqVO {

    @ApiModelProperty(value = "ID")
    @NotNull(message = "ID不能为空")
    private Long id;

    {{#each fields}}
    @ApiModelProperty(value = "{{this.description}}")
    {{#if this.required}}
    @NotBlank(message = "{{this.description}}不能为空")
    {{#unless this.noValidation}}
    @Length(min = {{this.minLength}}, max = {{this.maxLength}}, message = "{{this.description}}长度需在 {{this.minLength}} 到 {{this.maxLength}} 之间")
    {{/unless}}
    {{/if}}
    private {{this.type}} {{this.name}};
    {{/each}}
}

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: 删除{{entityName}} VO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "删除{{entityName}} VO")
public class Delete{{entityName}}ReqVO {

    @ApiModelProperty(value = "ID")
    @NotNull(message = "ID不能为空")
    private Long id;
}

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: 查询{{entityName}}分页 VO
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "查询{{entityName}}分页 VO")
public class Find{{entityName}}PageListReqVO extends BasePageQuery {

    {{#each queryFields}}
    @ApiModelProperty(value = "{{this.description}}")
    private {{this.type}} {{this.name}};
    {{/each}}
}

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: 查询{{entityName}}详情 VO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询{{entityName}}详情 VO")
public class Find{{entityName}}DetailReqVO {

    @ApiModelProperty(value = "ID")
    @NotNull(message = "ID不能为空")
    private Long id;
}