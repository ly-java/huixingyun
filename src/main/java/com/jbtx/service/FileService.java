package com.jbtx.service;

import com.github.pagehelper.PageInfo;
import com.jbtx.entity.Files;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @file: FileService
 * @Description:
 * @Author: ls
 * @Date: 2019/12/9 21:23
 */
public interface FileService {
    boolean upload(Long id, String name, MultipartFile file);

    /**
     * 根据文件名模糊查询分页获取文件信息
     *
     * @param filename 文件名
     * @return
     */
    PageInfo<Files> getAllFiles(Integer currentPage, Integer pageSize, String filename);

    /**
     * 根据主键id下载文件
     *
     * @param id
     * @return
     */
    void downById(Long id, HttpServletResponse response);

    /**
     * 根据主键id删除文件的方法
     *
     * @param id 主键id
     * @return 成功返回true 失败返回false
     */
    boolean isDelete(Long id);

}
