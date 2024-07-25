package com.example.facebook_integration.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "`user`") // Escaping the table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "First Name is required.")
    private String firstName;
    @NotBlank(message = "Last Name is required.")
    private String lastName;
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@dal\\.ca$", message = "Invalid email. Only @dal.ca addresses are accepted.")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter.")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter.")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one number.")
    @Pattern(regexp = ".*[@$!%*?&].*", message = "Password must contain at least one special character (@, $, !, %, *, ?, &).")
    private String password;
    @NotNull(message = "Date of Birth is required.")
    private String dateOfBirth;
    private String bio;
    private String profilePic;
    @NotBlank(message = "Security Answer is required.")
    private String securityAnswer;

    public enum Status {
        Available,
        Busy,
        Away,
        Offline
    }

    public Status status = Status.Available;

    // Constructor
    public User() {
    }

    public User(int id, String firstName, String lastName, String email, String password, String bio, String dateOfBirth, String profilePic, String securityAnswer) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
        this.profilePic = profilePic;
        this.securityAnswer = securityAnswer;
    }

    // Getters and setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
