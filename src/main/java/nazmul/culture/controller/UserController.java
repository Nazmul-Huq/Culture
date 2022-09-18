package nazmul.culture.controller;

import nazmul.culture.domain.User;
import nazmul.culture.dto.UserDto;
import nazmul.culture.service.IService.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/user")
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * SAVE A USER
     * @param userDto
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        User newUser = userService.save(user);
        return new ResponseEntity<>("User created successfully" + newUser, HttpStatus.OK);
    }

    /**
     * GET ALL USER
     * @return
     */
    @GetMapping("/get-user")
    public ResponseEntity<List<UserDto>> getUser(){
        List<UserDto> users = new ArrayList<>();
        userService.findAll().forEach(user -> {
            UserDto userdto = new UserDto();
            userdto.setId(user.getId());
            userdto.setName(user.getName());
            users.add(userdto);
        });
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * UPDATE AN USER
     * NOTE: only user's name can be updated. No other field is updateable
     * @param userDto
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDto userDto){
        Optional<User> userToUpdate = userService.findById(userDto.getId());
        if (userToUpdate.isPresent()) {
            userToUpdate.get().setName(userDto.getName());
            userService.save(userToUpdate.get());
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User can not updated", HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE AN USER
     * NOTE: when we delete a user, associated reviews will also be deleted
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId){
        Optional<User> userToDelete = userService.findById(userId);
        if (userToDelete.isPresent()) {
            userService.deleteById(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found with Id: " + userId, HttpStatus.NO_CONTENT);
    }


} // class ends here
