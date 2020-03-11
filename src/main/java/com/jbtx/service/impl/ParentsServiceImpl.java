package com.jbtx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbtx.dao.ParentsMapper;
import com.jbtx.entity.Parents;
import com.jbtx.exception.ValidationException;
import com.jbtx.service.ParentsService;
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
public class ParentsServiceImpl implements ParentsService {
    @Autowired
    private ParentsMapper parentsMapper;

    /**
     * 根据学生姓名分页获取家长列表的方法
     *
     * @param currentPage  当前页
     * @param pageSize     显示条数
     * @param studentsname 学生名字（模糊查询）
     * @return
     */
    @Override
    public PageInfo<Parents> getAllParents(Integer currentPage, Integer pageSize, String studentsname) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo<Parents>(parentsMapper.selectAllParents(studentsname));
    }

    /**
     * 根据主键判断该家长是否能删除
     *
     * @param id 家长id
     * @return 可以删除返回true，不可以删除返回false
     */
    @Override
    public boolean isDelete(Long id) {
        return parentsMapper.countById(id) == 0;
    }

    /**
     * 根据主键逻辑删除家长的方法
     *
     * @param id 家长id
     * @return 删除成功返回true，删除失败返回false
     */
    @Override
    public boolean delete(Long id) {
        Parents parents = new Parents();
        parents.setId(id);
        parents.setIsdelete("2");
        parents.setUpdateid(1L);
        parents.setUpdatetime(new Date());
        return parentsMapper.updateByPrimaryKeySelective(parents) == 1;
    }

    /**
     * @param parents 家长对象
     * @return 执行结果 添加成功 ：返回刚插入的主键id 添加失败：返回888 ,777：修改成功 666：修改失败
     */
    @Override
    public Long addOrUpdateParents(Parents parents) {
        if (parents.getId() == null) {
            //添加
            parents.setCreateid(1L);
            parents.setCreatetime(new Date());
            parents.setIsdelete("1");
            parents.setAccount(parents.getParentsphone());
            int i = parentsMapper.insertSelective(parents);
            if (i == 1) {
                return parents.getId();
            } else {
                return 888L;
            }
        } else {
            //修改
            parents.setAccount(parents.getParentsphone());
            parents.setUpdatetime(new Date());
            parents.setUpdateid(1L);
            return parentsMapper.updateByPrimaryKeySelective(parents) == 1 ? 777L : 666L;
        }
    }

    /**
     * 根据手机号判断该用户是否可用
     *
     * @param parentsphone 手机号
     * @return 返回结果 true 可用 false 不可用
     */
    @Override
    public boolean checkPhone(String parentsphone) {
        Parents parents = new Parents();
        parents.setParentsphone(parentsphone);
        return parentsMapper.selectCount(parents) == 0;
    }

    @Override
    public Parents selectByid(Long id) {
        return parentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int export(String studentsname, HttpServletResponse response) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("家长信息表");
            List<Parents> list = parentsMapper.selectAllParents(studentsname);
            String fileName = "家长信息表.xls";
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
            String[] headers = {"ID", "姓名", "性别", "手机号", "孩子姓名", "关系", "家庭住址", "创建日期"};
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
            /*"ID", "姓名", "性别", "手机号", "孩子姓名", "关系", "家庭住址", "创建日期"*/
            for (Parents parents : list) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.setHeight((short) (25 * 20));
                row1.createCell(0).setCellValue(parents.getId());
                row1.createCell(1).setCellValue(parents.getParentsname());
                row1.createCell(2).setCellValue(parents.getParentssex().equals("1") ? "男" : "女");
                row1.createCell(3).setCellValue(parents.getParentsphone());
                row1.createCell(4).setCellValue(parents.getStudentsname() == null ? "" : parents.getStudentsname());
                row1.createCell(5).setCellValue(parents.getRelation());
                row1.createCell(6).setCellValue(parents.getParentsaddress());
                row1.createCell(7).setCellValue(sdf.format(parents.getCreatetime()));
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

    @Override
    public boolean login(String phone, String password) {
        Parents parents = new Parents();
        parents.setParentsphone(phone);
        Parents login = parentsMapper.selectOne(parents);
        return login.getPassword().equals(password);
    }

    @Override
    public boolean isExist(String phone) {
        Parents parents = new Parents();
        parents.setParentsphone(phone);
        return parentsMapper.selectOne(parents) != null;
    }
}
