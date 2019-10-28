package com.chirping.gmall.pojo;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 刘志鹏
 * @date 2019/10/28
 */
@Data
public class PmsSearchParam implements Serializable {
    private String catalog3Id;
    private String keyword;
    private List<PmsSkuAttrValue> skuAttrValueList;
    private static final long serialVersionUID = 1L;
}
