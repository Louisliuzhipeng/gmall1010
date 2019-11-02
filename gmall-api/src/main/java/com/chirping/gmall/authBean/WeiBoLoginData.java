package com.chirping.gmall.authBean;

import lombok.Data;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Data
public class WeiBoLoginData {

    private String avatar;

    private String blog;

    private String gender;

    private String location;

    private String nickname;

    private String remark;

    private String source;

    private WeiBoLoginToken token;

    private String username;

    private String uuid;

    @Override
    public String toString() {
        return "WeiBoLoginData{" +
                "avatar='" + avatar + '\'' +
                ", blog='" + blog + '\'' +
                ", gender='" + gender + '\'' +
                ", location='" + location + '\'' +
                ", nickname='" + nickname + '\'' +
                ", remark='" + remark + '\'' +
                ", source='" + source + '\'' +
                ", username='" + username + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
