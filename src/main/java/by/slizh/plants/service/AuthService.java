package by.slizh.plants.service;

import by.slizh.plants.web.dto.auth.JwtRequest;
import by.slizh.plants.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
