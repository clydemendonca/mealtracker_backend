package com.calorie.mealtracker.model.response;

import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginResponseBody extends StandardResponseBody {

    public static final String MESSAGE = "Successfully logged in";

    private MealtrackerUserToReturn user;

    public LoginResponseBody(MealtrackerUser user) {
        super(StandardResponseBody.SUCCESS, MESSAGE);
        this.user = new MealtrackerUserToReturn(user);
    }

    public void setToken(String token) {
        user.setToken(token);
    }

    public MealtrackerUserToReturn getUser() {
        return user;
    }

    public void setUser(MealtrackerUserToReturn user) {
        this.user = user;
    }

    public class MealtrackerUserToReturn {

        private MealtrackerUser.Role role;
        private String username;
        private String fullName;
        private String token;

        public MealtrackerUserToReturn(MealtrackerUser user) {
            this.username = user.getUsername();
            this.fullName = user.getFullName();
            this.token = token;
            this.role = user.getRole();
        }

        public MealtrackerUser.Role getRole() {
            return role;
        }

        public void setRole(MealtrackerUser.Role role) {
            this.role = role;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
