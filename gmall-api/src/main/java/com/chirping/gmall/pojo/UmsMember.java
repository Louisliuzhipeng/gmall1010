package com.chirping.gmall.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
public class UmsMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String memberLevelId;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private int status;
    private Date createTime;
    private String icon;
    private int gender;
    private Date birthday;
    private String city;
    private String job;
    private String personalizedSignature;
    private long sourceUid;
    private int sourceType;
    private int integration;
    private int growth;
    private int luckeyCount;
    private String accessToken;
    private String accessCode;
    private Integer historyIntegration;

    private static final long serialVersionUID = 1L;
}