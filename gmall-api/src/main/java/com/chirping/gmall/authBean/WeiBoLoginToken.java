package com.chirping.gmall.authBean;

import lombok.Data;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Data
public class WeiBoLoginToken {

    private String accesstoken;

    private int expirein;

    private String openid;

    private Long uid;

    @Override
    public String toString() {
        return "WeiBoLoginToken{" +
                "accesstoken='" + accesstoken + '\'' +
                ", expirein=" + expirein +
                ", openid='" + openid + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
