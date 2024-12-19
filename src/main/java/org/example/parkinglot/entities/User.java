package org.example.parkinglot.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Collections;

@Entity

public class User {


    private String email;
    private String password;
    private String username;
    @OneToMany(mappedBy = "owner")
    private Collection<Car> cars;

    @Id
    @GeneratedValue
    private Long id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Basic
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Collection<Car> getCars() {
        return cars;
    }

    public void setCars(Collection<Car> cars) {
        this.cars = cars;
    }
}