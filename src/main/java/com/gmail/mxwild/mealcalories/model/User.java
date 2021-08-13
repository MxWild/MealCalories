package com.gmail.mxwild.mealcalories.model;


import org.hibernate.validator.constraints.Range;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static com.gmail.mxwild.mealcalories.common.Constants.DEFAULT_CALORIES_PER_DAY;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")
        })
public class User extends NamedEntity {

    @Email
    @Size(max = 180)
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(min = 5, max = 100)
    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean, default true")
    private boolean isEnabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp, default now()")
    private Date registered = new Date();

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"user_id", "role"},
                            name = "user_roles_unique_idx")
            })
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Range(min = 10, max = 10000)
    @Column(name = "calories_per_day", nullable = false, columnDefinition = "integer, default 2000")
    private int caloriesPerDay;

    public User() {
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled(), user.getRegistered(), user.getCaloriesPerDay(), user.getRoles());
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, true, new Date(), DEFAULT_CALORIES_PER_DAY, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, boolean isEnabled, Date registered, int caloriesPerDay, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.registered = registered;
        this.caloriesPerDay = caloriesPerDay;
        this.roles = roles;
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
