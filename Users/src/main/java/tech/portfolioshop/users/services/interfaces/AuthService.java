package tech.portfolioshop.users.services.interfaces;

import tech.portfolioshop.users.shared.UserDto;


public interface AuthService {
    UserDto signup(UserDto userDetails);
    UserDto signin(UserDto userDetails);
}
