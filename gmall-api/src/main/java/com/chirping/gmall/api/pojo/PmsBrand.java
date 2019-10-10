package com.chirping.gmall.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

@Data
@TableName(value = "pms_brand")
public class PmsBrand implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "name")
    private String name;

    /**
     * 首字母
     */
    @TableField(value = "first_letter")
    private String firstLetter;

    @TableField(value = "sort")
    private Integer sort;

    /**
     * 是否为品牌制造商：0->不是；1->是
     */
    @TableField(value = "factory_status")
    private Integer factoryStatus;

    @TableField(value = "show_status")
    private Integer showStatus;

    /**
     * 产品数量
     */
    @TableField(value = "product_count")
    private Integer productCount;

    /**
     * 产品评论数量
     */
    @TableField(value = "product_comment_count")
    private Integer productCommentCount;

    /**
     * 品牌logo
     */
    @TableField(value = "logo")
    private String logo;

    /**
     * 专区大图
     */
    @TableField(value = "big_pic")
    private String bigPic;

    /**
     * 品牌故事
     */
    @TableField(value = "brand_story")
    private String brandStory;

    private static final long serialVersionUID = 1L;
}