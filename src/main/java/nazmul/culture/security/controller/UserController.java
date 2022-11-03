package nazmul.culture.security.controller;

import nazmul.culture.security.model.SignupRequest;
import nazmul.culture.security.model.SignupResponse;
import nazmul.culture.security.model.Role;
import nazmul.culture.security.model.User;
import nazmul.culture.domain.Venue;
import nazmul.culture.dto.*;
import nazmul.culture.security.service.IRoleService;
import nazmul.culture.security.service.IUserService;
import nazmul.culture.service.IService.IVenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.*;

@RestController()
@RequestMapping("/api/user")
public class UserController {

    private IUserService userService;
    private IVenueService venueService;
    private IRoleService roleService;

    public UserController(IUserService userService, IVenueService venueService, IRoleService roleService) {
        this.userService = userService;
        this.venueService = venueService;
        this.roleService = roleService;
    }

    /**
     * SAVE A USER
     * @return
     */
    @PermitAll
    @PostMapping("/save")
    public ResponseEntity<SignupResponse> createUser(@RequestBody SignupRequest signupRequest){

        Set<Role> rolesToAdd = new HashSet<>(); // create a set to hold all roles
        // if requested roles are not empty, then add Role to "rolesToAdd" variable
        if ( !(signupRequest.getRoles().isEmpty()) ) {
            String roles = signupRequest.getRoles();
            String[] roleIds =roles.split(",");
            for (String roleId:roleIds) {
                long roleIdAsLong=Long.parseLong(roleId);
                Role role = new Role();
                role.setId(roleIdAsLong);
                rolesToAdd.add(role);
            }
        }

        //create the new user and set properties
        User user = new User();
        user.setName(signupRequest.getName());
        user.setUsername(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());
        user.setRoles(rolesToAdd);

        //save the user and return message based on user is saved or not
        User newUser = userService.save(user);
        if (newUser != null) return ResponseEntity.ok(new SignupResponse("SUCCESS"));
        return ResponseEntity.ok(new SignupResponse("FAILED"));

    } //createUser() ends here

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
            userdto.setUsername(user.getUsername());
            Set<String> roles = new HashSet<>();
            user.getRoles().forEach( role -> roles.add(role.getName()));
            userdto.setRoles(roles);
            users.add(userdto);
        });
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * UPDATE AN USER
     * NOTE: only user's name can be updated. No other field is updateable
     * @param signupRequest
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/update")
    public ResponseEntity<SignupResponse> update(@RequestBody SignupRequest signupRequest){

        Optional<User> userToUpdate = userService.findById(signupRequest.getId());
        if (userToUpdate.isPresent()) {
            Set<Role> rolesToAdd = new HashSet<>(); // create a set to hold all roles
            if ( !(signupRequest.getRoles().isEmpty()) ) {
                String roles = signupRequest.getRoles();
                String[] roleIds =roles.split(",");
                for (String roleId:roleIds) {
                    long roleIdAsLong=Long.parseLong(roleId);
                    Role role = new Role();
                    role.setId(roleIdAsLong);
                    rolesToAdd.add(role);
                }
            }

            //create the new user and set properties
            userToUpdate.get().setName(signupRequest.getName());
            userToUpdate.get().setPassword(signupRequest.getPassword());
            userToUpdate.get().setRoles(rolesToAdd);

            userService.save(userToUpdate.get());
            return ResponseEntity.ok(new SignupResponse("SUCCESS"));
        } else {
            return ResponseEntity.ok(new SignupResponse("FAILED"));
        }
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/find-user-by-id/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        UserDto userDto = new UserDto();
        if (user.isPresent()) {
        userDto.setId(user.get().getId());
        userDto.setName(user.get().getName());
        userDto.setUsername(user.get().getUsername());
        userDto.setPassword(user.get().getPassword());
        Set<String> roles = new HashSet<>();
        user.get().getRoles().forEach( role -> roles.add(role.getName()));
        userDto.setRoles(roles);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/find-user-by-name")
    public ResponseEntity<List<UserDto>> findUserByName(@RequestBody UserSearchRequest userSearchRequest){
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> users = userService.findByName(userSearchRequest.getName());
        users.forEach(user -> {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setUsername(user.getUsername());
            Set<String> roles = new HashSet<>();
            user.getRoles().forEach( role -> roles.add(role.getName()));
            userDto.setRoles(roles);
            userDtoList.add(userDto);
        });
        return new ResponseEntity(userDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/find-user-by-username")
    public ResponseEntity<UserDto> findUserByUserName(@RequestBody UserSearchRequest userSearchRequest){
        User user = userService.findByUsername(userSearchRequest.getName());
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach( role -> roles.add(role.getName()));
        userDto.setRoles(roles);
        return new ResponseEntity(userDto, HttpStatus.OK);
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

} // class ends here
