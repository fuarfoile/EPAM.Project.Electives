package com.boast.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** Сущность пользователя*/
@Entity
@Table(name = "user")
public class User {

    @Id
    private int id = -1;
    private Position position;
    private String name;
    private String surname;
    private String email;
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
