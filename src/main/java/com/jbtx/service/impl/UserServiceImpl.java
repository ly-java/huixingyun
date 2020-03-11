package com.jbtx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbtx.dao.UserMapper;
import com.jbtx.entity.User;
import com.jbtx.exception.ValidationException;
import com.jbtx.service.UserService;
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

/**
 * @file: UserServiceImpl
 * @Description:
 * @Author: ls
 * @Date: 2019/12/14 22:52
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据当前页、显示条数、用户名、角色id查找中心人员信息的方法
     *
     * @param currentPage 当前页
     * @param pageSize    显示条数
     * @param username    用户名（模糊查询）
     * @param roleid      角色id（精确查询）
     * @return
     */
    @Override
    public PageInfo<User> selectAll(Integer currentPage, Integer pageSize, String username, Long roleid) {
        PageHelper.startPage(currentPage, pageSize);
        return new PageInfo<User>(userMapper.seleteAll(username, roleid, null));
    }

    /**
     * 添加或修改中心人员的方法
     *
     * @param user
     * @return
     */
    @Override
    public int addOrUpdateUser(User user) {
        //判断user.id是否为null，为null进入添加方法，不为null进入修改方法
        if (user.getId() == null) {
            //添加方法
            user.setUseraccount(user.getUserphone());
            user.setEntrytime(new Date());
            user.setCreatetime(new Date());
            user.setCreateid(1L);
            user.setIsdelete("1");
            return userMapper.insertSelective(user) == 1 ? 666 : 555;
        } else {
            //修改方法
            //设置中心人员账户与手机号一致
            user.setUseraccount(user.getUserphone());
            user.setUpdatetime(new Date());
            user.setUpdateid(1L);
            return userMapper.updateByPrimaryKeySelective(user) == 1 ? 444 : 333;
        }
    }

    /**
     * 逻辑删除中心人员的方法
     *
     * @param id 要进行逻辑删除的中心人员信息
     * @return 删除结果 666：删除成功 111：删除失败
     */
    @Override
    public boolean deleteUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setIsdelete("2");
        user.setUpdateid(1L);
        user.setUpdatetime(new Date());
        return userMapper.updateByPrimaryKeySelective(user) == 1;
    }

    /**
     * 根据中心人员id查找信息的方法
     *
     * @param id 中心人员id
     * @return 中心人员对象
     */
    @Override
    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectAllUser() {
        List<User> list = userMapper.seleteAll(null, null, "type");
        return list;
    }

    /**
     * 根据手机号判断该用户是否存在
     *
     * @param userphone
     * @return
     */
    @Override
    public boolean selectByPhone(String userphone) {
        User user = new User();
        user.setUserphone(userphone);
        return userMapper.selectCount(user) == 0;
    }

    @Override
    public int export(String username, Long roleid, HttpServletResponse response) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("中心人员信息表");
            List<User> list = userMapper.seleteAll(username, roleid, null);
            String fileName = "中心人员信息表.xls";
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
            String[] headers = {"ID", "姓名", "性别", "身份证", "手机号", "住址", "代课教师", "入职时间", "职位"};
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
            /*"ID", "姓名", "性别", "身份证", "手机号", "住址", "入职时间", "职位"*/
            for (User user : list) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                HSSFRow row1 = sheet.createRow(rowNum);
                row1.setHeight((short) (25 * 20));
                row1.createCell(0).setCellValue(user.getId());
                row1.createCell(1).setCellValue(user.getUsername());
                row1.createCell(2).setCellValue(user.getUsersex().equals("1") ? "男" : "女");
                row1.createCell(3).setCellValue(user.getUserid());
                row1.createCell(4).setCellValue(user.getUserphone());
                row1.createCell(5).setCellValue(user.getUseraddress());
                row1.createCell(6).setCellValue(sdf.format(user.getCreatetime()));
                row1.createCell(7).setCellValue(user.getRoles().getRolename());
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
    public boolean isDelete(Long id) {
        return userMapper.selectById(id) == 0;
    }

    @Override
    public boolean login(String phone, String password) {
        User user = new User();
        user.setUserphone(phone);
        User login = userMapper.selectOne(user);
        return login.getPassword().equals(password);
    }
}
