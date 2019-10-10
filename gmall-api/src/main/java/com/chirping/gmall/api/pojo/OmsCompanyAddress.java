package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "oms_company_address")
public class OmsCompanyAddress implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 地址名称
     */
    @TableField(value = "address_name")
    private String addressName;

    /**
     * 默认发货地址：0->否；1->是
     */
    @TableField(value = "send_status")
    private Integer sendStatus;

    /**
     * 是否默认收货地址：0->否；1->是
     */
    @TableField(value = "receive_status")
    private Integer receiveStatus;

    /**
     * 收发货人姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 收货人电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 省/直辖市
     */
    @TableField(value = "province")
    private String province;

    /**
     * 市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 区
     */
    @TableField(value = "region")
    private String region;

    /**
     * 详细地址
     */
    @TableField(value = "detail_address")
    private String detailAddress;

    private static final long serialVersionUID = 1L;
}