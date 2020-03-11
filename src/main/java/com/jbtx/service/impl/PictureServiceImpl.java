package com.jbtx.service.impl;

import com.jbtx.dao.PicturesMapper;
import com.jbtx.dao.StudentsMapper;
import com.jbtx.entity.Pictures;
import com.jbtx.entity.Students;
import com.jbtx.exception.ValidationException;
import com.jbtx.service.PictureService;
import com.jbtx.service.SuCaiService;
import com.jbtx.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private StudentsMapper studentsMapper;
    @Autowired
    private PicturesMapper picturesMapper;
    @Autowired
    private SuCaiService suCaiService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addPicture(Long id, String picturedescribe, MultipartFile files) {
        Students students = studentsMapper.selectByPrimaryKey(id);
        if (students == null) {
            throw new ValidationException("该学生不存在");
        }
        try {
            File file = FileUtil.uploadFile(2, students.getClassname(), students.getStudentsname(), files);
            Long sucaiid = suCaiService.addSuCai(files.getOriginalFilename(), file.getPath(), "2");
            Pictures pictures = new Pictures();
            pictures.setStudentid(students.getId());
            pictures.setPicturedescribe(picturedescribe);
            pictures.setIsdelete("1");
            pictures.setCreatetime(new Date());
            pictures.setSucaiid(sucaiid);
            pictures.setCreateid(1L);
            return picturesMapper.insertSelective(pictures) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("上传失败！");
        }
    }
}
