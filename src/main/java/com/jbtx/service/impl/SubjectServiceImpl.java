package com.jbtx.service.impl;

import com.jbtx.dao.SubjectsMapper;
import com.jbtx.entity.Subjects;
import com.jbtx.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectsMapper subjectsMapper;

    /**
     * 获取所有科目列表
     *
     * @return 科目列表
     */
    @Override
    public List<Subjects> getAll() {
        Subjects subjects = new Subjects();
        subjects.setIsdelete("1");
        List<Subjects> list = subjectsMapper.select(subjects);
        return list;
    }

    /**
     * 根据科目id查询科目信息
     *
     * @param id
     * @return
     */
    @Override
    public Subjects getById(Long id) {
        return subjectsMapper.selectByPrimaryKey(id);
    }

    /**
     * 增加修改通用的方法
     *
     * @param subjects 传入的参数，根据id判断是增加还是修改
     * @return 执行结果 999：新增课程成功 888：新增课程失败 777：修改课程成功 666：修改课程失败
     */
    @Override
    public int updateSubject(Subjects subjects) {
        if (subjects.getId() != null) {
            //进入修改方法
            subjects.setUpdateid(1L);
            subjects.setUpdatetime(new Date());
            return subjectsMapper.updateByPrimaryKeySelective(subjects) == 1 ? 777 : 666;
        } else {
            //进入添加方法
            System.out.println(subjects.getSubjectname());
            subjects.setCreatetime(new Date());
            subjects.setCreateid(1L);
            subjects.setIsdelete("1");
            return subjectsMapper.insertSelective(subjects) == 1 ? 999 : 888;
        }
    }

    /**
     * 根据主键id判断该课程是否可以删除
     *
     * @param id 科目id
     * @return 可以删除返回true，不可以删除返回false
     */
    @Override
    public boolean isdelete(Long id) {
        return subjectsMapper.countById(id) == 0;
    }

    /**
     * 根据主键id删除课程
     *
     * @param id 课程id
     * @return 删除成功返回true，删除失败返回false
     */
    @Override
    public boolean delete(Long id) {
        Subjects subjects = new Subjects();
        subjects.setId(id);
        subjects.setUpdateid(1L);
        subjects.setUpdatetime(new Date());
        subjects.setIsdelete("2");
        return subjectsMapper.updateByPrimaryKeySelective(subjects) == 1;
    }

    /**
     * 判断课程名是否可用
     *
     * @param subjectname 课程名
     * @return true 可用 false不可用
     */
    @Override
    public boolean checkSubjectName(String subjectname) {
        Subjects subjects = new Subjects();
        subjects.setSubjectname(subjectname);
        return subjectsMapper.selectCount(subjects) == 0;
    }
}
