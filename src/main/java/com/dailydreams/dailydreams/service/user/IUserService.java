package com.dailydreams.dailydreams.service.user;

import com.dailydreams.dailydreams.Dtos.UserDto;
import com.dailydreams.dailydreams.model.User;
import com.dailydreams.dailydreams.request.CreateUserRequest;
import com.dailydreams.dailydreams.request.UpdateUserRequest;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long id);
    void deleteUser(Long id);

    UserDto convertToUserDto(User user);
}
