package com.anurag.electronic_store.Elecsto.services.implementation;

import com.anurag.electronic_store.Elecsto.dtos.UserDto;
import com.anurag.electronic_store.Elecsto.entities.User;
import com.anurag.electronic_store.Elecsto.exceptions.ResourceNotFoundException;
import com.anurag.electronic_store.Elecsto.repositories.UserRepository;
import com.anurag.electronic_store.Elecsto.services.UserService;
import lombok.Builder;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        UserDto ourDto = entityToDto(savedUser);
        return ourDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given ID!"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        User updatedUser = userRepository.save(user);
        return entityToDto(updatedUser);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given ID!"));

        //Delete user profile image
        String fullPath = imagePath+user.getImageName();
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }
        catch (NoSuchFileException e)
        {
            logger.info("User image not found");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        //Delete user
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUser(int pageNumber , int pageSize , String sortBy , String sortDirection) {

//  Normal Implementation which will return all Users .
//        List<User> allUsers = userRepository.findAll();
//        List<UserDto> dtoList = new ArrayList<>();
//        for (User user : allUsers) {
//            dtoList.add(entityToDto(user));
//        }

//  This way will return Users using pagination & Also in a particularly sorted manner.
        Sort sort = (sortDirection.equalsIgnoreCase("dsc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> pageOfUser= userRepository.findAll(pageable);
        List<UserDto> dtoList = new ArrayList<>();
        for (User user : pageOfUser) {
            dtoList.add(entityToDto(user));
        }

        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given ID!"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found!"));
        return entityToDto(user);
    }


    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .imageName(savedUser.getImageName())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender()).build();

        return mapper.map(savedUser,UserDto.class);
    }
    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .imageName(userDto.getImageName())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender()).build();
        return mapper.map(userDto,User.class);
    }
}
