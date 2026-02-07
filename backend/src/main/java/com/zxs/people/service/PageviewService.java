package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.Pageview;
import com.zxs.people.entity.vo.pageview.PageviewAddVo;
import com.zxs.people.entity.vo.pageview.PageviewQueryVo;
import com.zxs.people.entity.vo.pageview.PageviewUpdateVo;

/**
* @Author: zxs
* @Date: 2025-12-23 17:38:38
* @ClassName: PageviewService
* @Version: 1.0
* @Description: 浏览量 服务层
*/
public interface PageviewService extends IService<Pageview> {
    /**
     * 分页查询
     *
     * @param pageviewQueryVo 分页查询实体
     * @return PageResult<Pageview>
     */
    PageResult<Pageview> pageviewPage(PageviewQueryVo pageviewQueryVo);

    /**
     * 新增
     *
     * @param pageviewAddVo 新增实体
     * @return Boolean
     */
    Boolean pageviewAdd(PageviewAddVo pageviewAddVo);

    /**
     * 修改
     *
     * @param pageviewUpdateVo 修改实体
     * @return Boolean
     */
    Boolean pageviewUpdate(PageviewUpdateVo pageviewUpdateVo);
}
