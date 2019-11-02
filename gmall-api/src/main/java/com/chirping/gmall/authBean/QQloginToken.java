package com.chirping.gmall.authBean;

import lombok.Data;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Data
public class QQloginToken {
    private String accesstoken;

    private int expirein;

    private String openid;

    private String refreshtoken;

    @Override
    public String toString() {
        return "QQloginToken{" +
                "accesstoken='" + accesstoken + '\'' +
                ", expirein=" + expirein +
                ", openid='" + openid + '\'' +
                ", refreshtoken='" + refreshtoken + '\'' +
                '}';
    }
}
