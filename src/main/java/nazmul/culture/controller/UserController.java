package nazmul.culture.controller;

import nazmul.culture.domain.User;
import nazmul.culture.service.IService.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user/save")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.OK);
    }
}
