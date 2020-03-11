package com.jbtx.service.impl;

import com.jbtx.dao.ClassRoomMapper;
import com.jbtx.entity.ClassRoom;
import com.jbtx.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private ClassRoomMapper classRoomMapper;

    /**
     * 根据教师名字和课程id查找班级信息
     *
     * @param username  教师名字（模糊查询）
     * @param subjectid 课程id（精确查询）
     * @return 班级列表
     */
    @Override
    public List<ClassRoom> getAll(String username, Long subjectid) {
        List<ClassRoom> list = classRoomMapper.selectAllClassRoom(username, subjectid);
        return list;
    }

    /**
     * 添加班级的方法
     *
     * @param classRoom 班级对象
     * @return 执行结果 777：添加成功 666：添加失败
     */
    @Override
    public int addClass(ClassRoom classRoom) {
        classRoom.setCreatetime(new Date());
        classRoom.setCreateid(1L);
        classRoom.setIsdelete("1");
        return classRoomMapper.insertSelective(classRoom) == 1 ? 777 : 666;
    }

    /**
     * 删除班级的方法
     *
     * @param id 班级id
     * @return 删除成功返回true，删除失败返回false
     */
    @Override
    public boolean deleteClass(Long id) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(id);
        classRoom.setUpdateid(1L);
        classRoom.setUpdatetime(new Date());
        classRoom.setIsdelete("2");
        return classRoomMapper.updateByPrimaryKeySelective(classRoom) == 1;
    }

    /**
     * 判断班级是否可以删除的方法
     *
     * @param id 班级id
     * @return 可以删除返回true，不可以删除返回false
     */
    @Override
    public boolean isdeleteClass(Long id) {
        return classRoomMapper.countById(id) == 0;
    }

    /**
     * 根据班级名字查询班级信息
     *
     * @param classname
     * @return
     */
    @Override
    public boolean checkName(String classname) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setClassname(classname);
        return classRoomMapper.selectCount(classRoom) == 0;
    }

    /**
     * 根据课程id返回班级列表
     *
     * @param subjectid
     * @return
     */
    @Override
    public List<ClassRoom> getAllClassBySubjectId(Long subjectid) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setSubjectid(subjectid);
        List<ClassRoom> list = classRoomMapper.select(classRoom);
        return list;
    }
}
