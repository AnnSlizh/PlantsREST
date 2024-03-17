package by.slizh.plants.web.dto.auth;

import by.slizh.plants.web.dto.user.UserDto;
import lombok.Data;

@Data
public class JwtResponse {

    private UserDto userDto;
    private String accessToken;
    private String refreshToken;
}
