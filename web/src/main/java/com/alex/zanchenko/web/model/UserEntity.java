package com.alex.zanchenko.web.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String email;
    String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
                joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    List<Role> roles = new ArrayList<>();
    // because we always need this role whenever we're actually going
    // to pull a user from the database we're
    // going to set this to eager because we
    // always we're always going to need this
    // role and we don't want to use lazy
    // loading because if we're going to need
    // it every single time that a user
    // accesses a page we should just set it to
    // eager so that it loads it always instead
    // of lazy loading where it only loads it

}
