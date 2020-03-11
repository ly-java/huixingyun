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
@Table(name = "roles")
public class Roles {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String rolename;
@JsonFormat(  pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
private Date createtime;

private Long createid;
@JsonFormat(  pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
private Date updatetime;

private Long updateid;

private String isdelete;

private String beizhuone;

private String beizhutwo;
        }