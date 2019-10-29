package com.chirping.gmall.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 刘志鹏
 * @date 2019/10/29
 */
@Data
public class PmsSearchCrumb implements Serializable {

    private String valueId;
    private String urlParam;
    private String valueName;
    private static final long serialVersionUID = 1L;
}
