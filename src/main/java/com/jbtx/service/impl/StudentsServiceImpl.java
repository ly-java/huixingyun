package com.jbtx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbtx.dao.StudentsMapper;
import com.jbtx.entity.Students;
import com.jbtx.exception.ValidationException;
import com.jbtx.service.StudentsService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StudentsServiceImpl implements StudentsService {
    @Autowired
    StudentsMapper studentsMapper;

    /**
     * 添加或修改学生的方法
     *
     * @param students 学生对象
     * @return 添加成功返回999 添加失败返回888 修改成功返回777 修改失败返回666
     */
    @Override
    public int addOrUpdateStudents(Students students) {
        if (students.getId() == null) {
            students.setCreateid(1L);
            students.setCreatetime(new Date());
            students.setIsdelete("1");
            students.setIsstudy("1");
            return studentsMapper.insertSelective(students) == 1 ? 999 : 888;
        } else {
            students.setUpdateid(1L);
            students.setUpdatetime(new Date());
            return studentsMapper.updateByPrimaryKeySelective(students) == 1 ? 777 : 666;
        }
    }

    /**
     * 根据id逻辑删除学生的方法
     *
     * @param id 学生id
     * @return 删除成功返回999 删除失败返回888
     */
    @Override
    public int deleteStudents(Long id) {
        Students students = studentsMapper.selectByPrimaryKey(id);
        students.setUpdatetime(new Date());
        students.setUpdateid(1L);
        students.setIsdelete("2");
        return studentsMapper.updateByPrimaryKeySelective(students) == 1 ? 999 : 888;
    }

    @Override
    public boolean isdelete(Long id) {
        Students students = studentsMapper.selectByPrimaryKey(id);
        return "2".equals(students.getIsstudy());
    }

    /**
     * genuine家长id获取孩子列表
     *
     * @param parentsid 家长id
     * @return 孩子列表
     */
    @Override
    public List<Students> getChildren(Long parentsid) {
        List<Students> list = studentsMapper.getChildren(parentsid);
        System.out.println(list.get(0).getClassname());
        return list;
    }

    @Override
    public PageInfo<Students> getStudents(Integer currentPage, Integer pageSize, String studentsname, Long teacherid, Long classid) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo<Students>(studentsMapper.getStudents(studentsname, teacherid, classid));
    }

    /**
     * 根据学生主键id获取学生对象
     *
     * @param id 主键id
     * @return 学生对象
     */
    @Override
    public Students selectById(Long id) {
        return studentsMapper.selectById(id);
    }

    /**
     * 导出学生
     *
     * @param studentsname 学生姓名（模糊查询）
     * @param teacherid    教师id （精确查询）
     * @param response
     */
    @Override
    public int export(String studentsname, Long teacherid, Long classid, HttpServletResponse response) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("学生信息表");
            List<Students> list = studentsMapper.getStudents(studentsname, teacherid, classid);
            String fileName = "学生信息表.xls";
            //Sheet样式
            HSSFCellStyle sheetStyle = workbook.createCellStyle();
            //背景色的设定
            sheetStyle.setFillBackgroundColor(HSSFColor.ROYAL_BLUE.index);
            //前景色的设定
            sheetStyle.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
            //左右居中
            sheetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //上下居中
            sheetStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            sheetStyle.setLocked(true);
            sheetStyle.setWrapText(true);
            //设置列的样式
            for (int i = 0; i <= 30; i++) {
                sheet.setDefaultColumnStyle((short) i, sheetStyle);
            }
            //新增数据航，并且设置单元格数据
            int rowNum = 1;
            String[] headers = {"ID", "姓名", "性别", "生日", "学校", "班级名称", "代课教师", "在读状态"};
            //headers表示excel表中第一行的表头
            HSSFRow row = sheet.createRow(0);
            //在excel表中添加表头
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                row.setHeight((short) (25 * 25));
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
                //new ScheduleTemplteSetSheetStyle().setSheetFrameStyle(workbook,sheet,1,30)
            }
            //设置列宽
            for (int i = 0; i <= 30; i++) {
                sheet.setColumnWidth(i, 4000);
                if (i > 28) sheet.setColumnWidth(i, 7000);
            }
            for (Students s : list) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.setHeight((short) (25 * 20));
                row1.createCell(0).setCellValue(s.getId());
                row1.createCell(1).setCellValue(s.getStudentsname());
                row1.createCell(2).setCellValue(s.getStudentssex().equals("1") ? "男" : "女");
                row1.createCell(3).setCellValue(sdf.format(s.getBorntime()));
                row1.createCell(4).setCellValue(s.getSchool());
                row1.createCell(5).setCellValue(s.getClassname());
                row1.createCell(6).setCellValue(s.getUsername());
                row1.createCell(7).setCellValue(s.getIsstudy().equals("1") ? "在读" : "肄业");
                rowNum++;
            }
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
            return 999;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ValidationException("导出失败！");
        }
    }
}
