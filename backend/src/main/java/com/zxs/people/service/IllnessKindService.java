package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.IllnessKind;
import com.zxs.people.entity.vo.illnessKind.IllnessKindAddVo;
import com.zxs.people.entity.vo.illnessKind.IllnessKindQueryVo;
import com.zxs.people.entity.vo.illnessKind.IllnessKindUpdateVo;

/**
* @Author: zxs
* @Date: 2025-12-23 17:38:38
* @ClassName: IllnessKindService
* @Version: 1.0
* @Description: 疾病种类 服务层
*/
public interface IllnessKindService extends IService<IllnessKind> {
    /**
     * 分页查询
     *
     * @param illnessKindQueryVo 分页查询实体
     * @return PageResult<IllnessKind>
     */
    PageResult<IllnessKind> illnessKindPage(IllnessKindQueryVo illnessKindQueryVo);

    /**
     * 新增
     *
     * @param illnessKindAddVo 新增实体
     * @return Boolean
     */
    Boolean illnessKindAdd(IllnessKindAddVo illnessKindAddVo);

    /**
     * 修改
     *
     * @param illnessKindUpdateVo 修改实体
     * @return Boolean
     */
    Boolean illnessKindUpdate(IllnessKindUpdateVo illnessKindUpdateVo);
}
