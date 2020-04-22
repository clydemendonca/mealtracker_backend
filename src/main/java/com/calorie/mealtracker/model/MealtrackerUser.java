package com.calorie.mealtracker.model;

public class MealtrackerUser {

    private long id;
    private String username;
    private String encryptedPassword;
    private String fullName;
    private Role role;

    public MealtrackerUser(long id, String username, String encryptedPassword, String fullName, Role role) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.fullName = fullName;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role mealtrackerUserRole) {
        this.role = mealtrackerUserRole;
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

    public enum Role {

        ADMIN(1000),
        USER(1);

        private int numValue;

        Role(int numValue) {
            this.numValue = numValue;
        }

        public int getNumValue() {
            return numValue;
        }

    }


}
