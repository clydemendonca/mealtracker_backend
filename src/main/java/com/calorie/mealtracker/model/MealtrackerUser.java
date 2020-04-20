package com.calorie.mealtracker.model;

public class MealtrackerUser {

    private String username;
    private String encryptedPassword;
    private String fullName;

    public MealtrackerUser(String username, String encryptedPassword, String fullName) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
