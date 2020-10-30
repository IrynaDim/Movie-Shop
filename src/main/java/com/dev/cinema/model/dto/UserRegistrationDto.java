package com.dev.cinema.model.dto;

import com.dev.cinema.validation.Email;
import com.dev.cinema.validation.FieldsValueMatch;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldsValueMatch(field = "password",
        fieldMatch = "repeatPassword")
public class UserRegistrationDto {
    @NotNull(message = "Email could not be null")
    @Email(message = "Incorrect email format!")
    private String email;
    @NotNull(message = "Password could not be null")
    @Size(min = 8)
    private String password;
    private String repeatPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
