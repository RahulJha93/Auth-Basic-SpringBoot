package com.learning.auth.auth_app_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

@Getter // Lombok generates getters for all fields
@Setter // Lombok generates setters for all fields
@Builder // Lombok generates a builder for this class (flexible object creation)
@AllArgsConstructor // Lombok generates a constructor with all fields as parameters
@NoArgsConstructor // Lombok generates a no-argument constructor (required by Hibernate)
@Entity // Tells Hibernate/JPA that this class represents a database table
@Table(name = "users") // Maps this entity to the table 'users'; if omitted, table name defaults to class name 'User'
public class User implements UserDetails {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.UUID)  // Auto-generate the ID using UUID
    @Column(name = "user_id")  // Custom column name in the database
    private UUID id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email", unique = true, length = 300) // Unique constraint and max length
    private String email;

    private String password; // (note: variable name should start lowercase 'password')
    private String image;

    // private String gender; // commented out
    // private Address address; // commented out

    private boolean enable = true; // Default value true

    private Instant createdAt = Instant.now(); // Track creation time
    private Instant updatedAt = Instant.now(); // Track last update time

    @Enumerated(EnumType.STRING) // Store enum as string in DB (e.g., "LOCAL") instead of numeric index
    private Provider provider = Provider.LOCAL;

    @ManyToMany(fetch = FetchType.EAGER) // Many-to-many relationship with Role; roles are fetched eagerly
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    ) // Specifies join table and foreign key columns
    private Set<Role> roles = new HashSet<>();

    @PrePersist // Automatically called by Hibernate before inserting a new record
    protected void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;
    }

    @PreUpdate // Automatically called by Hibernate before updating a record
    protected void onUpdate() {
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now; // Safety check
        updatedAt = now;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles
                .stream()
                .map(role-> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }
}
