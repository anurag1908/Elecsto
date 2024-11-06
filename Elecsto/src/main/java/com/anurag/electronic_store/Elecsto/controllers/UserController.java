package com.anurag.electronic_store.Elecsto.controllers;

import com.anurag.electronic_store.Elecsto.dtos.ApiResponseMessage;
import com.anurag.electronic_store.Elecsto.dtos.ImageResponse;
import com.anurag.electronic_store.Elecsto.dtos.UserDto;
import com.anurag.electronic_store.Elecsto.services.FileService;
import com.anurag.electronic_store.Elecsto.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.OutputKeys;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable("userId") String userId ){
         UserDto updatedUserDto = userService.updateUser(userDto,userId);
         return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Deleted Successfully ")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDirection",defaultValue = "asc",required = false) String sortDirection

    ){
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDirection), HttpStatus.OK);
    }

    //get single by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage") MultipartFile image,
            @PathVariable String userId
    ) throws IOException
    {
        String imageName = fileService.uploadImage(image,imageUploadPath);

        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto updatedUser = userService.updateUser(user,userId);

        ImageResponse imageResponse = ImageResponse.
                builder()
                .imageName(imageName)
                .success(true)
                .status(HttpStatus.CREATED)
                .message("Uploaded")
                .build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }


}
