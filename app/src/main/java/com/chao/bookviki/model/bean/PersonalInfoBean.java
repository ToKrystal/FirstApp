package com.chao.bookviki.model.bean;

/**
 * Created by Administrator on 2017/4/17.
 */

public class PersonalInfoBean {
    private String nickName;
    private String signUpName;
    private String simpleDesc;

    public PersonalInfoBean(String nickName, String signUpName, String simpleDesc) {
        this.nickName = nickName;
        this.signUpName = signUpName;
        this.simpleDesc = simpleDesc;
    }

    public PersonalInfoBean() {
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignUpName() {
        return signUpName;
    }

    public void setSignUpName(String signUpName) {
        this.signUpName = signUpName;
    }

    public String getSimpleDesc() {
        return simpleDesc;
    }

    public void setSimpleDesc(String simpleDesc) {
        this.simpleDesc = simpleDesc;
    }
}
