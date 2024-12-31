package com.ngdat.cukcuklite.data.models;

public class User {
    private String UserId;
    private String Name;
    private String Password;
    private String Email;
    private String AvatarUrl;

    public User() {
    }

    public User(Builder builder) {
        UserId = builder.UserId;
        Name = builder.Name;
        Password = builder.Password;
        Email = builder.Email;
        AvatarUrl = builder.AvatarUrl;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        AvatarUrl = avatarUrl;
    }

    public static class Builder {
        private String UserId;
        private String Name;
        private String Password;
        private String Email;
        private String AvatarUrl;

        public Builder setUserId(String userId) {
            UserId = userId;
            return this;
        }

        public Builder setName(String name) {
            Name = name;
            return this;
        }

        public Builder setPassword(String password) {
            Password = password;
            return this;
        }

        public Builder setEmail(String email) {
            Email = email;
            return this;
        }

        public Builder setAvatarUrl(String avatarUrl) {
            AvatarUrl = avatarUrl;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
