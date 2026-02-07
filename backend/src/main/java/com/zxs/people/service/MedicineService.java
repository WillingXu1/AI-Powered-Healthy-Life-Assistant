package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.Medicine;
import com.zxs.people.entity.vo.medicine.MedicineAddVo;
import com.zxs.people.entity.vo.medicine.MedicineQueryVo;
import com.zxs.people.entity.vo.medicine.MedicineUpdateVo;

/**
* @Author: zxs
* @Date: 2025-12-23 17:38:38
* @ClassName: MedicineService
* @Version: 1.0
* @Description: 药品 服务层
*/
public interface MedicineService extends IService<Medicine> {
    /**
     * 分页查询
     *
     * @param medicineQueryVo 分页查询实体
     * @return PageResult<Medicine>
     */
    PageResult<Medicine> medicinePage(MedicineQueryVo medicineQueryVo);

    /**
     * 新增
     *
     * @param medicineAddVo 新增实体
     * @return Boolean
     */
    Boolean medicineAdd(MedicineAddVo medicineAddVo);

    /**
     * 修改
     *
     * @param medicineUpdateVo 修改实体
     * @return Boolean
     */
    Boolean medicineUpdate(MedicineUpdateVo medicineUpdateVo);
}
