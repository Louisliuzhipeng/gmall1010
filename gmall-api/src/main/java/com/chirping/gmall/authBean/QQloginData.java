package com.chirping.gmall.authBean;

import lombok.Data;

/**
 * @author 刘志鹏
 * @date $date$
 */
@Data
public class QQloginData {
    private String avatar;

    private int gender;

    private String location;

    private String nickname;

    private String source;

    private QQloginToken token;

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
