package com.dailydreams.dailydreams.service.user;

import com.dailydreams.dailydreams.Dtos.UserDto;
import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.User;
import com.dailydreams.dailydreams.repository.UserRepository;
import com.dailydreams.dailydreams.request.CreateUserRequest;
import com.dailydreams.dailydreams.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("User Not Found!"));

    }

    @Override
    public User createUser(CreateUserRequest request) {

        return Optional.of(request).filter(user->!userRepository.existsByEmail(request.getEmail()))
                .map(req->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(()->new ResourceNotFoundException(request.getEmail()+ "email already exist!"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long id) {
        return userRepository.findById(id).map(existingUser->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return  userRepository.save(existingUser);
        }).orElseThrow(()->new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete, ()-> {throw new ResourceNotFoundException("User Not Found!");
                });


    }

    @Override
    public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
