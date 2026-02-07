package com.zxs.people.entity.vo.pageview;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @Author: zxs
* @Date: 2025-12-23 17:38:38
* @ClassName: PageviewAddVo
* @Version: 1.0
* @Description: 浏览量新增实体
*/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "浏览量新增实体")
public class PageviewAddVo implements Serializable {

    /**
     * 浏览量
     */
    @TableField("pageviews")
    @Schema(name = "pageviews",description = "浏览量",type = "int")
    private Integer pageviews;

    /**
     * 病的id
     */
    @TableField("illness_id")
    @Schema(name = "illnessId",description = "病的id",type = "int")
    private Integer illnessId;

}
