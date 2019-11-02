package com.chirping.gmall.authBean;

import lombok.Data;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Data
public class QQloginData {
    private String avatar;

    private String gender;

    private String location;

    private String nickname;

    private String source;

    private QQloginToken token;

    private String username;

    private String uuid;

    public void setGender(String gender) {
        if (gender.equals("FEMALE")) {
            this.gender = "女";
        } else if (gender.equals("MALE")) {
            this.gender = "男";
        } else {
            this.gender = "保密";
        }

    }

    @Override
    public String toString() {
        return "QQloginData{" +
                "avatar='" + avatar + '\'' +
                ", gender='" + gender + '\'' +
                ", location='" + location + '\'' +
                ", nickname='" + nickname + '\'' +
                ", source='" + source + '\'' +
                ", username='" + username + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
