package com.example.instaserve;

public class feedback {
    String username;
    String feedback;

    @Override
    public String toString() {
        return "feedback{" +
                "username='" + username + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
