package com.TaskTrackingManagementSystem.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class LoginRequestDto {

     @Email
    @NotBlank
    private String email;

    @Column(name = "Password", nullable = false, unique = true)
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String Password;

    public LoginRequestDto() {
    }
    public LoginRequestDto( String email, String Password) {
        this.email = email;
        this.Password = Password;
       
    }


    //setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setpassword(String Password) {
        this.Password = Password;
    }

    public String getEmail() {
        return email;
    }

    public String getpassword() {
        return Password;
    }
}
