package com.jbtx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data //虚拟生成get/set方法
@NoArgsConstructor //无参构造方法
@AllArgsConstructor //全参构造方法
@Table(name = "user")//对应数据库中的表名
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String useraccount;

    private String username;

    private String userid;

    private String usersex;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date entrytime;

    private String userphone;

    private String useraddress;

    private Long roleid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    private Long createid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatetime;

    private Long updateid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endtime;

    private String isdelete;

    private String password;

    private String beizhuone;

    private String beizhutwo;

    //临时字段
    private Roles roles;
    private ClassRoom classRoom;
}