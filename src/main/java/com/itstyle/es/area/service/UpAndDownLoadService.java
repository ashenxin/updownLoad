package com.itstyle.es.area.service;

import com.itstyle.es.area.dao.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 上传下载Service
 */
public interface UpAndDownLoadService {

    /**
     * 上传
     * @param file
     * @return
     */
    Result upLoad(MultipartFile file);

    /**
     * 下载
     * @return
     */
    Result downLoad(HttpServletResponse response, String fileName);
}
