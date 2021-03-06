package com.calorie.mealtracker.model.request;

public class SignUpRequestBody {

    private String username;
    private String password;
    private String fullName;

    public SignUpRequestBody(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isUsernameEmpty() {
        return username == null || username.isEmpty();
    }

    public boolean isPasswordEmpty() {
        return password == null || password.isEmpty();
    }

    public boolean isFullNameEmpty() {
        return fullName == null || fullName.isEmpty();
    }
}
