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

    private int gender;

    private String location;

    private String nickname;

    private String remark;

    private String source;

    private WeiBoLoginToken token;

    private String username;

    private Long uuid;

    public void setGender(String gender) {
        if (gender.equals("FEMALE")) {
            this.gender = 2;
        } else if (gender.equals("MALE")) {
            this.gender = 1;
        } else {
            this.gender = 0;
        }

    }

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
