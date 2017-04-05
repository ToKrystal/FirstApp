package com.chao.bookviki.model.bean;

/**
 * Created by Jessica on 2017/4/4.
 */

public class CreateAccountBean {
    public String name;
    public String password;
    public String email;

    public CreateAccountBean(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
