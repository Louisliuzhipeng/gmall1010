package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "ums_member_receive_address")
public class UmsMemberReceiveAddress implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "member_id")
    private Long memberId;

    /**
     * 收货人名称
     */
    @TableField(value = "name")
    private String name;

    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 是否为默认
     */
    @TableField(value = "default_status")
    private Integer defaultStatus;

    /**
     * 邮政编码
     */
    @TableField(value = "post_code")
    private String postCode;

    /**
     * 省份/直辖市
     */
    @TableField(value = "province")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 区
     */
    @TableField(value = "region")
    private String region;

    /**
     * 详细地址(街道)
     */
    @TableField(value = "detail_address")
    private String detailAddress;
}