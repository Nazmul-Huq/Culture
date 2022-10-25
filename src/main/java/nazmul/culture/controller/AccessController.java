package nazmul.culture.controller;

import lombok.AllArgsConstructor;
import nazmul.culture.domain.User;
import nazmul.culture.domain.UserDetailsImpl;
import nazmul.culture.dto.LoginRequest;
import nazmul.culture.dto.LoginResponse;
import nazmul.culture.service.IService.IUserService;
import nazmul.culture.service.ServiceImpl.UserDetailsServiceImpl;
import nazmul.culture.utility.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
@AllArgsConstructor
public class AccessController {

    private UserDetailsServiceImpl userDetailsService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private IUserService userService;


    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<LoginResponse> createToken(@RequestBody LoginRequest request) throws Exception {
        System.out.println(" JwtController createToken Call: 4" + request.getUsername());

        // first check if user exist or not, if not send username not found
        User user = userService.findByUsername(request.getUsername());
        if (user==null){
            return ResponseEntity.ok(new LoginResponse("BAD_CREDENTIAL"));
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),
                            request.getPassword())
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new LoginResponse("BAD_CREDENTIAL"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        final String jwtToken = jwtUtils.generateJwtToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }
}
