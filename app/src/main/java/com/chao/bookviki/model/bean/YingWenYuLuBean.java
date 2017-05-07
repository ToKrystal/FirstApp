package com.chao.bookviki.model.bean;

/**
 * Created by Jessica on 2017/5/7.
 */

public class YingWenYuLuBean {
    private String english;
    private String chinese;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return "YingWenYuLuBean{" +
                "english='" + english + '\'' +
                ", chinese='" + chinese + '\'' +
                '}';
    }
}
