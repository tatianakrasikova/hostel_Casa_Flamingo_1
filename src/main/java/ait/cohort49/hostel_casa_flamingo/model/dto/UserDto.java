package ait.cohort49.hostel_casa_flamingo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Schema(description = "DTO representing a user")
public class UserDto {

    @Schema(
            description = "Unique identifier of the user",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "User's first name",
            example = "John"
    )
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Doe"
    )
    private String lastName;

    @Schema(
            description = "User's email address",
            example = "john.doe@example.com"
    )
    private String email;

    @Schema(
            description = "User's telephone number",
            example = "+1234567890"
    )
    private String tel;

    @Schema(description = "Set of roles assigned to the user")
    private Set<RoleDto> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto user = (UserDto) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("User: id - %d, firstName - %s, lastName - %s, email - %s, tel - %s, roles - %s",
                id, firstName, lastName, email, tel, roles == null ? "[]" : roles);
    }
}
