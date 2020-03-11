package com.jbtx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentsname;

    private String studentssex;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date borntime;

    private String school;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    private Long createid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatetime;

    private Long updateid;

    private Long parentsid;

    private Long classid;

    private String isstudy;

    private String isdelete;

    private String beizhuone;

    private String beizhutwo;

    //临时字段
    @Transient
    private String classname;
    @Transient
    private String username;

}