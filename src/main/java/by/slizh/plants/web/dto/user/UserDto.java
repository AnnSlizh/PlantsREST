package by.slizh.plants.web.dto.user;

import by.slizh.plants.domain.user.Role;
import by.slizh.plants.web.validation.OnCreate;
import by.slizh.plants.web.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {

    @NotNull(message = "Id should be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Username should be not null", groups = {OnCreate.class})
    @Length(max = 256, message = "Username should be smaller than 256 symbols", groups = {OnCreate.class})
    private String username;

    @NotNull(message = "Name should be not null", groups = {OnCreate.class})
    @Length(max = 256, min = 2, message = "Name should be smaller than 200 symbols and longer than 2", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password should be not null", groups = {OnCreate.class})
    @Length(max = 1024, min = 4, message = "Password should be smaller than 1024 symbols and longer than 4", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Role> roles;
}
