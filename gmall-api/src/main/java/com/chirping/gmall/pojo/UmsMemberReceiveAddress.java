package com.chirping.gmall.pojo;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author 15211
 */
@Data
public class UmsMemberReceiveAddress implements Serializable {

    @Id
    private String id;
    private String memberId;
    private String name;
    private String phoneNumber;
    private int defaultStatus;
    private String postCode;
    private String province;
    private String city;
    private String region;
    private String detailAddress;
    private static final long serialVersionUID = 1L;
}
