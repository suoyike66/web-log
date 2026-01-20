package com.suoyike.weblog.admin.service;

import com.suoyike.weblog.common.utils.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-19 20:41
 * @description: TODO
 **/
public interface AdminFileService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    Response uploadFile(MultipartFile file);
}
