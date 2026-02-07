package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.Feedback;
import com.zxs.people.entity.vo.feedback.FeedbackAddVo;
import com.zxs.people.entity.vo.feedback.FeedbackQueryVo;
import com.zxs.people.entity.vo.feedback.FeedbackUpdateVo;

/**
* @Author: zxs
* @Date: 2025-12-23 17:38:38
* @ClassName: FeedbackService
* @Version: 1.0
* @Description: 反馈 服务层
*/
public interface FeedbackService extends IService<Feedback> {
    /**
     * 分页查询
     *
     * @param feedbackQueryVo 分页查询实体
     * @return PageResult<Feedback>
     */
    PageResult<Feedback> feedbackPage(FeedbackQueryVo feedbackQueryVo);

    /**
     * 新增
     *
     * @param feedbackAddVo 新增实体
     * @return Boolean
     */
    Boolean feedbackAdd(FeedbackAddVo feedbackAddVo);

    /**
     * 修改
     *
     * @param feedbackUpdateVo 修改实体
     * @return Boolean
     */
    Boolean feedbackUpdate(FeedbackUpdateVo feedbackUpdateVo);
}
