package by.slizh.plants.web.contoller.auth;

import by.slizh.plants.domain.user.User;
import by.slizh.plants.service.UserService;
import by.slizh.plants.service.impl.AuthServiceImpl;
import by.slizh.plants.web.dto.auth.JwtRequest;
import by.slizh.plants.web.dto.auth.JwtResponse;
import by.slizh.plants.web.dto.user.UserDto;
import by.slizh.plants.web.mapper.UserMapper;
import by.slizh.plants.web.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthServiceImpl authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public JwtResponse register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        System.out.println("In register controller");
        User user = userMapper.toEntity(userDto);
        userService.create(user);
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(userDto.getUsername());
        jwtRequest.setPassword(userDto.getPassword());
        return authService.login(jwtRequest);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}