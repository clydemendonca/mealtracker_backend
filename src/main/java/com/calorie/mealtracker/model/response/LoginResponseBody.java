package com.calorie.mealtracker.model.response;

import com.calorie.mealtracker.model.MealtrackerUser;

public class LoginResponseBody extends StandardResponseBody {

    public static final String MESSAGE = "Successfully logged in";

    private MealtrackerUserToReturn user;

    public LoginResponseBody(MealtrackerUser user) {
        super(StandardResponseBody.SUCCESS, MESSAGE);
        this.user = new MealtrackerUserToReturn(user);
    }

    public MealtrackerUserToReturn getUser() {
        return user;
    }

    public void setUser(MealtrackerUserToReturn user) {
        this.user = user;
    }

    public class MealtrackerUserToReturn {

        private String username;
        private String fullName;

        public MealtrackerUserToReturn(MealtrackerUser user) {
            this.username = user.getUsername();
            this.fullName = user.getFullName();
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
    }

}
