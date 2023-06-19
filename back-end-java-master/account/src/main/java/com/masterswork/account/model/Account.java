package com.masterswork.account.model;

import com.masterswork.account.model.base.AuditedEntity;
import com.masterswork.account.model.enumeration.EmailActivationState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
public class Account extends AuditedEntity {

    @Id
    @Column(name = "account_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Pattern(
            regexp = "^[a-z](?:[a-z\\d]|_(?=[a-z\\d])){3,38}$",
            message = "Username must contain between 4 and 39 characters," +
                    " allowed characters: lowercase letters, numbers, underscore" +
                    " must start with lowercase letter" +
                    " can't contain two underscores in a row" +
                    " can't end with an underscore"
    )
    private String username;

    @NotNull
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s])[\\x00-\\xFF]{6,}$",
            message = "Password must contain " +
                    " at least six ASCII characters," +
                    " at least one number," +
                    " at least one lowercase letter," +
                    " at least one uppercase letter," +
                    " at least one special character"
    )
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault("0")
    private EmailActivationState emailActivationState = EmailActivationState.PENDING;

    @NotNull
    @ColumnDefault("true")
    private Boolean active = true;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    private AppUser user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Account addRole(Role role) {
        roles.add(role);
        role.getAccounts().add(this);
        return this;
    }

    public Account removeRole(Role role) {
        roles.remove(role);
        role.getAccounts().remove(this);
        return this;
    }

    public Account setUser(AppUser appUser) {
        this.user = appUser;
        appUser.setAccount(this);
        return this;
    }
}
