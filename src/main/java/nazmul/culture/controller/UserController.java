package nazmul.culture.controller;

import nazmul.culture.domain.User;
import nazmul.culture.domain.Venue;
import nazmul.culture.dto.UserDto;
import nazmul.culture.service.IService.IUserService;
import nazmul.culture.service.IService.IVenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/user")
public class UserController {

    private IUserService userService;
    private IVenueService venueService;

    public UserController(IUserService userService, IVenueService venueService) {
        this.userService = userService;
        this.venueService = venueService;
    }

    /**
     * SAVE A USER
     * @param userDto
     * @return
     */
    @PermitAll
    @PostMapping("/save")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        User newUser = userService.save(user);
        return new ResponseEntity<>("User created successfully" + newUser, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create-like")
    public ResponseEntity<String> createLike(@RequestParam Long userId,
                                             @RequestParam Long venueId){
        Optional<User> user = userService.findById(userId);
        Optional<Venue> venue = venueService.findById(venueId);
        if ( user.isPresent() && venue.isPresent()) {
            user.get().getVenueLiked().add(venue.get());
            userService.save(user.get());
            return new ResponseEntity<>("like added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("like not added", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET ALL USER
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId){
        Optional<User> userToDelete = userService.findById(userId);
        if (userToDelete.isPresent()) {
            userService.deleteById(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found with Id: " + userId, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/find-user-by-name/")
    public ResponseEntity<?> findUserByName(@RequestParam(name = "name") String name){
        return new ResponseEntity<>(userService.findByName(name), HttpStatus.OK);
    }


} // class ends here
