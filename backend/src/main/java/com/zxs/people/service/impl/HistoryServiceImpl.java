package com.zxs.people.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.History;
import com.zxs.people.entity.vo.history.HistoryAddVo;
import com.zxs.people.entity.vo.history.HistoryQueryVo;
import com.zxs.people.entity.vo.history.HistoryUpdateVo;
import com.zxs.people.mapper.HistoryMapper;
import com.zxs.people.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
* @Author: zxs
* @Date: 2025-12-23 17:38:38
* @ClassName: HistoryServiceImpl
* @Version: 1.0
* @Description: 操作记录 服务实现层
*/
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
@SuppressWarnings({"unchecked", "rawtypes"})
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Autowired
    private HistoryMapper historyMapper;

    @Override
    public PageResult<History> historyPage(HistoryQueryVo historyQueryVo) {
        LambdaQueryWrapper<History> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Optional.ofNullable(historyQueryVo.getUserId()).isPresent(),History::getUserId, historyQueryVo.getUserId());
        queryWrapper.eq(StringUtils.isNotBlank(historyQueryVo.getKeyword()),History::getKeyword, historyQueryVo.getKeyword());
        queryWrapper.eq(Optional.ofNullable(historyQueryVo.getOperateType()).isPresent(),History::getOperateType, historyQueryVo.getOperateType());

        //分页数据
        Page<History> page = new Page<>(historyQueryVo.getPageNum(),historyQueryVo.getPageSize());
        //查询数据
        Page<History> pageNew = historyMapper.selectPage(page, queryWrapper);
        //返回分页数据
        return new PageResult<>(pageNew.getRecords(), pageNew.getTotal(), pageNew.getPages(), historyQueryVo.getPageNum(), historyQueryVo.getPageSize());
    }

    @Override
    public Boolean historyAdd(HistoryAddVo historyAddVo){
        //创建实体对象
        History history = new History();
        //复制属性
        BeanUtils.copyProperties(historyAddVo, history);
        //插入数据
        return historyMapper.insert(history) > 0 ? true : false;
    }

    @Override
    public Boolean historyUpdate(HistoryUpdateVo historyUpdateVo){
        //根据ID查询数据
        History byId=this.getById(historyUpdateVo.getId());
        //判断数据是否存在
        if(Optional.ofNullable(byId).isEmpty()){
            log.error("数据不存在");
            return false;
        }
        //复制属性
        BeanUtils.copyProperties(historyUpdateVo, byId);
        //修改数据
        return historyMapper.updateById(byId) > 0 ? true : false;
    }
}
