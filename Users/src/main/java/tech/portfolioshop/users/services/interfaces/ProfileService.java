package tech.portfolioshop.users.services.interfaces;

import tech.portfolioshop.users.shared.UserDto;

public interface ProfileService {
    UserDto getUserDetailsByEmail(String email);
    UserDto getUserByUserId(String userId);
    void updateUser(UserDto userDetails);
    void deleteUser(String userId);
}
