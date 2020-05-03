package com.calorie.mealtracker.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class MealtrackerUser {

    @MongoId
    private ObjectId id;

    private String username;
    private String encryptedPassword;
    private String fullName;
    private Role role;
    private float dailyCalorieIntake;

    public MealtrackerUser() {
    }

    public MealtrackerUser(String username, String encryptedPassword, String fullName, Role role) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.fullName = fullName;
        this.role = role;
    }

    public MealtrackerUser(String id, String username, String encryptedPassword, String fullName, Role role) {
        this.id = new ObjectId(id);
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.fullName = fullName;
        this.role = role;
    }

    public MealtrackerUser(String id, String username, String encryptedPassword, String fullName, Role role, float dailyCalorieIntake) {
        this.id = new ObjectId(id);
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.fullName = fullName;
        this.role = role;
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public float getDailyCalorieIntake() {
        return dailyCalorieIntake;
    }

    public void setDailyCalorieIntake(float dailyCalorieIntake) {
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public String getId() {
        return id.toHexString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
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

        ADMIN,
        USER;

    }


}
