package com.dailydreams.dailydreams.controller;

import com.dailydreams.dailydreams.Dtos.UserDto;
import com.dailydreams.dailydreams.exception.AlreadyExistsException;
import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.User;
import com.dailydreams.dailydreams.request.CreateUserRequest;
import com.dailydreams.dailydreams.request.UpdateUserRequest;
import com.dailydreams.dailydreams.response.ApiResponse;
import com.dailydreams.dailydreams.service.user.IUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;


    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable  Long userId){

        try {
            User user = userService.getUserById(userId);
            UserDto responseDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", responseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
    }



    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){

        try {
            User user = userService.createUser(request);
            UserDto responseDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("User Create Success!", responseDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage (), null));
        }


    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody  UpdateUserRequest request,@PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            UserDto responseDto = userService.convertToUserDto(user);
            return ResponseEntity.ok(new ApiResponse("User Update Success!", responseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }


    }


    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){

        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User Delete Success!", userId));
        } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
