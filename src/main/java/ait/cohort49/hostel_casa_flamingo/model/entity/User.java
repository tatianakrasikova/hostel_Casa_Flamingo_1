package ait.cohort49.hostel_casa_flamingo.model.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Column(name = "tel", nullable = false, length = 15)
    private String tel;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Cart cart;

    public User(String firstName, String lastName, String email, String tel, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
        this.password = password;
        this.roles.add(role);
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Column(name = "password")
    private String password;


    public boolean isIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(boolean active) {
        this.isConfirmed = active;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return isConfirmed == user.isConfirmed && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(tel, user.tel) && Objects.equals(roles, user.roles) && Objects.equals(cart, user.cart) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, tel, isConfirmed, roles, cart, password);
    }

    @Override
    public String toString() {
        return String.format("User: id - %d, firstName - %s, lastName - %s, email - %s, tel - %s, active - %s, roles - %s",
                id, firstName, lastName, email, tel, isConfirmed, roles == null ? "[]" : roles);
    }


}
