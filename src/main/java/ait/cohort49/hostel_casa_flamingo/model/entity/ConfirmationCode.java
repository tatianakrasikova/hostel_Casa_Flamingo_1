package ait.cohort49.hostel_casa_flamingo.model.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "confirm_code")
public class ConfirmationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "expired", nullable = false)
    private LocalDateTime expired;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ConfirmationCode() {
    }

    public ConfirmationCode(String code, LocalDateTime expired, User user) {
        this.code = code;
        this.expired = expired;
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("Confirm code id: %d, Code: $s, user_id: %s, expired: %s",
                id,
                user == null ? null : user.getId(),
                expired);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfirmationCode that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(expired, that.expired) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, expired, user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
