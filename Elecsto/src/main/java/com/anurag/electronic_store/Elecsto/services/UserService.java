package com.anurag.electronic_store.Elecsto.services;
import com.anurag.electronic_store.Elecsto.dtos.UserDto;
import com.anurag.electronic_store.Elecsto.entities.User;

import java.util.List;


public interface UserService {

    //create user
    UserDto createUser(UserDto user);

    //update user
    UserDto updateUser(UserDto userDto,String userId);

    //delete user
    void deleteUser(String userId);

    //get all users
    List<UserDto> getAllUser(int pageNumber , int pageSize , String sortBy , String sortDirection);

    //get Single User
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String email);






}