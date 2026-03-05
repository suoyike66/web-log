package {{commonPackage}}.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: {{date}}
 * @description: {{entityName}}
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_{{entityNameLowerFirst}}")
public class {{entityName}}DO {

    @TableId(type = IdType.AUTO)
    private Long id;

    {{#each fields}}
    private {{this.type}} {{this.name}};
    {{/each}}

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;
}