package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.History;
import com.zxs.people.entity.vo.history.HistoryAddVo;
import com.zxs.people.entity.vo.history.HistoryQueryVo;
import com.zxs.people.entity.vo.history.HistoryUpdateVo;

/**
* @Author: zxs
* @Date: 2025-12-23 17:38:38
* @ClassName: HistoryService
* @Version: 1.0
* @Description: 操作记录 服务层
*/
public interface HistoryService extends IService<History> {
    /**
     * 分页查询
     *
     * @param historyQueryVo 分页查询实体
     * @return PageResult<History>
     */
    PageResult<History> historyPage(HistoryQueryVo historyQueryVo);

    /**
     * 新增
     *
     * @param historyAddVo 新增实体
     * @return Boolean
     */
    Boolean historyAdd(HistoryAddVo historyAddVo);

    /**
     * 修改
     *
     * @param historyUpdateVo 修改实体
     * @return Boolean
     */
    Boolean historyUpdate(HistoryUpdateVo historyUpdateVo);
}
