package com.gmail.mxwild.mealcalories.model;

import java.util.Date;
import java.util.Set;

import static com.gmail.mxwild.mealcalories.util.MealsUtil.CALORIES_PER_DAY;

public class User extends NamedEntity {

    private String email;

    private String password;

    private boolean isEnabled = true;

    private Date registered = new Date();

    private Set<Role> roles;

    private int caloriesPerDay = CALORIES_PER_DAY;

    public User(Integer id, String name, String email, String password, boolean isEnabled, Date registered, Set<Role> roles, int caloriesPerDay) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.registered = registered;
        this.roles = roles;
        this.caloriesPerDay = caloriesPerDay;
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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", registered=" + registered +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                ", id=" + id +
                '}';
    }
}
