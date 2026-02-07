package com.zxs.people.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxs.people.entity.KnowledgeFile;
import com.zxs.people.common.model.PageResult;
import com.zxs.people.entity.vo.knowledge.KnowledgeFileQueryVo;
import com.zxs.people.entity.vo.knowledge.KnowledgeFileUpdateVo;
import org.springframework.web.multipart.MultipartFile;

public interface KnowledgeFileService extends IService<KnowledgeFile> {
    KnowledgeFile saveToKnowledge(MultipartFile file, Integer createBy, String remark);

    PageResult<KnowledgeFile> page(KnowledgeFileQueryVo vo);

    boolean updateRemark(KnowledgeFileUpdateVo vo);

    boolean removeWithFileById(Long id);

    boolean removeWithFileByIds(java.util.List<Long> ids);
}
