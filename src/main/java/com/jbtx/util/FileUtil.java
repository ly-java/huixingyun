package com.jbtx.util;

import com.jbtx.common.Constans;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Year;
import java.util.Objects;

/**
 * 文件工具类
 * @author lq
 * @date 2020/1/10 15:13
 */
public class FileUtil {

    /**
     * 获取文件上传路径
     * @param type 1:作品上传2:图片上传
     * @return
     */
    public static String getFilePath(int type, String className, String studentName){
        //创建文件保存在本地的根路径
        String rootPath= Constans.ROOT_PATH;
        if(type==1){
            rootPath +=Constans.FILE_PATH;
        } else {
            rootPath += Constans.IMG_PATH;
        }
        // ./upload/file/2020/scratch1班/张无忌/
        rootPath += Year.now().toString()+"/"+className+"/"+studentName+"/";
        return rootPath;
    }

    /**
     * 上传文件
     * @param type 1:作品2：图片
     * @param className 班级名称
     * @param studentName 学生名称
     * @param file 文件
     * @return 上传之后的文件对象
     * @throws Exception
     */
    public static File uploadFile(int type, String className, String studentName, MultipartFile file) throws Exception{
        String path = getFilePath(type, className, studentName);
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
            System.out.println("创建文件夹");
        }
        File dest = new File(path, getNewFileName(Objects.requireNonNull(file.getOriginalFilename())));
        file.transferTo(dest);
        return dest;
    }

    /**
     * 获取新的文件名
     * @param oldName 旧文件名
     * @return 原文件"课堂照片.jpg">>=="课堂照片_20200110153252.jpg"
     */
    public static String getNewFileName(String oldName){
        String date = DateUtils.getNow();
        int i = oldName.lastIndexOf(".");
        //取得文件原名字
        String oldPre = oldName.substring(0, i);
        //取得文件扩展名
        String oldSuf = oldName.substring(i);
        return oldPre+"_"+date+oldSuf;
    }
}
