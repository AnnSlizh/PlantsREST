package by.slizh.plants.domain.user;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {


    private Long id;

    private String username;

    private String name;

    private String password;

    private Set<Role> roles;

}
