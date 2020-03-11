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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private Long studentid;
    @JsonFormat(  pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
    private Date createtime;

    private Long createid;
    @JsonFormat(  pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
    private Date updatetime;

    private Long updateid;

    private Long sucaiid;

    private String isdelete;

    private String beizhuone;

    private String beizhutwo;

    //临时字段
    private Sucai sucai;
}