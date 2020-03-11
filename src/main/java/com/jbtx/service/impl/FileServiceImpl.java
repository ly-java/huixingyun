package com.jbtx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbtx.dao.FilesMapper;
import com.jbtx.dao.StudentsMapper;
import com.jbtx.dao.SucaiMapper;
import com.jbtx.entity.Files;
import com.jbtx.entity.Students;
import com.jbtx.entity.Sucai;
import com.jbtx.exception.ValidationException;
import com.jbtx.service.FileService;
import com.jbtx.service.SuCaiService;
import com.jbtx.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @file: FileServiceImpl
 * @Description:
 * @Author: ls
 * @Date: 2019/12/9 21:24
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private StudentsMapper studentsMapper;
    @Autowired
    private SuCaiService suCaiService;
    @Autowired
    private FilesMapper filesMapper;
    @Autowired
    private SucaiMapper sucaiMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean upload(Long id, String name, MultipartFile files) {
        Students students = studentsMapper.selectByPrimaryKey(id);
        if (students == null) {
            throw new ValidationException("学生不存在！");
        }
        try {
            File file = FileUtil.uploadFile(1, students.getClassname(), students.getStudentsname(), files);
            Long sucaiid = suCaiService.addSuCai(files.getOriginalFilename(), file.getPath(), "1");
            Files files1 = new Files();
            files1.setCreateid(1L);
            files1.setCreatetime(new Date());
            files1.setFilename(name);
            files1.setIsdelete("1");
            files1.setStudentid(students.getId());
            files1.setSucaiid(sucaiid);
            return filesMapper.insertSelective(files1) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("添加失败");
        }
    }

    @Override
    public PageInfo<Files> getAllFiles(Integer currentPage, Integer pageSize, String filename) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo<Files>(filesMapper.selectAllFiles(filename));
    }

    @Override
    public void downById(Long id, HttpServletResponse response) {
        Files files = filesMapper.selectByPrimaryKey(id);
        if (files == null) {
            throw new ValidationException("文件ID不存在！");
        }
        Sucai sucai = sucaiMapper.selectByPrimaryKey(files.getSucaiid());
        File file = new File(sucai.getAddress());
        if (!file.exists()) {
            throw new ValidationException("文件不存在或已被删除！");
        }
        response.reset();
        byte[] downFile = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            os = response.getOutputStream();
            if (sucai.getType().equals("1")) {
                // 作品
                // 以流的形式下载文件。
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
                response.addHeader("Content-Length", "" + file.length());
                response.setContentType("application/octet-stream");
            }
            int i = 0;
            while ((i = bis.read(downFile)) != -1) {
                os.write(downFile, 0, i);
                os.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isDelete(Long id) {
        Files files = new Files();
        files.setId(id);
        files.setIsdelete("2");
        files.setUpdateid(1L);
        files.setUpdatetime(new Date());
        return filesMapper.updateByPrimaryKeySelective(files) == 1;
    }
}
