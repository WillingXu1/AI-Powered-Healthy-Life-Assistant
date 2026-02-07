package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.Files;
import com.zxs.people.entity.vo.files.FilesAddVo;
import com.zxs.people.entity.vo.files.FilesQueryVo;
import com.zxs.people.entity.vo.files.FilesUpdateVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
* @Author: zxs
* @Date: 2025-12-22 08:42:14
* @ClassName: FilesService
* @Version: 1.0
* @Description: 文件信息 服务层
*/
public interface FilesService extends IService<Files> {
    /**
     * 分页查询
     *
     * @param filesQueryVo 分页查询实体
     * @return PageResult<Files>
     */
    PageResult<Files> filesPage(FilesQueryVo filesQueryVo);

    /**
     * 新增
     *
     * @param filesAddVo 新增实体
     * @return Boolean
     */
    Boolean filesAdd(FilesAddVo filesAddVo);

    /**
     * 修改
     *
     * @param filesUpdateVo 修改实体
     * @return Boolean
     */
    Boolean filesUpdate(FilesUpdateVo filesUpdateVo);

    /**
     * 一个文件和一个文件名，将文件上传到MinIO
     *
     * @param file 要上传的文件，类型为MultipartFile
     * @return 返回一个Result对象，其中包含上传文件的临时URL
     */
    FilesAddVo updoadFileAndName(MultipartFile file);

    Map policy(String fileName);

    String getPolicyUrl(String fileName);

    String getUrl(String fileName);
}
