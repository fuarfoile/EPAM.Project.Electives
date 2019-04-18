package com.boast.transferobject;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    private int id;
    private Position position;

    @NotNull(message="Name is a required field")
    private String name;

    @NotNull(message="Surname is a required field")
    private String surname;

    @Email
    private String email;

    @NotNull(message="Password is a required field")
    @Size(min=4, max=16, message="Password must be equal to or greater than 4 characters and less than 16 characters")
    private String password;
    private String phoneNumber;
    private String passRecoveryKey;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public Position getPosition() { return this.position; }
    public void setPosition(Position position) { this.position = position; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return this.surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return this.phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassRecoveryKey() { return this.passRecoveryKey; }
    public void setPassRecoveryKey(String passRecoveryKey) { this.passRecoveryKey = passRecoveryKey; }

    @Override
    public String toString() {
        return "[" + name + " " + surname +
                ", id = " + id +
                ", email = " + email +
                ", phone number = " + phoneNumber +
                "]";
    }
}
