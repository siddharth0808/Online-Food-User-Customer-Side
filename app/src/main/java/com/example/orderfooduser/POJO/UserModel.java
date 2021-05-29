package com.example.orderfooduser.POJO;

public class UserModel {
    private String userName;
    private String userEmail;
    private String userPasssword;
    private String userMobile;
    public UserModel() {
    }

    public UserModel(String userName, String userEmail, String userPasssword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPasssword = userPasssword;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPasssword() {
        return userPasssword;
    }

    public void setUserPasssword(String userPasssword) {
        this.userPasssword = userPasssword;
    }

   public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
