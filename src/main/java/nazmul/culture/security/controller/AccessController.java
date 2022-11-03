package nazmul.culture.security.controller;

import lombok.AllArgsConstructor;
import nazmul.culture.security.model.RefreshToken;
import nazmul.culture.security.model.User;
import nazmul.culture.security.model.UserDetailsImpl;
import nazmul.culture.security.model.LoginRequest;
import nazmul.culture.security.model.LoginResponse;
import nazmul.culture.security.model.TokenRefreshRequest;
import nazmul.culture.security.model.TokenRefreshResponse;
import nazmul.culture.security.exception.TokenRefreshException;
import nazmul.culture.security.service.IRefreshTokenService;
import nazmul.culture.security.service.IUserService;
import nazmul.culture.security.service.UserDetailsServiceImpl;
import nazmul.culture.security.JwtUtils;
import nazmul.culture.security.RefreshTokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AccessController {

    private UserDetailsServiceImpl userDetailsService;
    private IUserService userService;
    private IRefreshTokenService refreshTokenService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private RefreshTokenUtils refreshTokenUtils;



    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<LoginResponse> createToken(@RequestBody LoginRequest request) throws Exception {
        System.out.println(" JwtController createToken Call: 4" + request.getUsername());

        // first check if user exist or not, if not send username not found
        User user = userService.findByUsername(request.getUsername());
        if (user==null){
            return ResponseEntity.ok(new LoginResponse("BAD_CREDENTIAL", new RefreshToken()));
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
            return ResponseEntity.ok(new LoginResponse("BAD_CREDENTIAL", new RefreshToken()));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        final String jwtToken = jwtUtils.generateJwtToken(userDetails);
        final RefreshToken refreshToken = refreshTokenUtils.generateRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new LoginResponse(jwtToken, refreshToken));
    } // createToken()/login ends here


    @PostMapping("/refresh-token")
    @PermitAll
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest){
      String providedRefreshToken = tokenRefreshRequest.getRefreshToken();
      return refreshTokenService.findByToken(providedRefreshToken)
              .map(refreshTokenUtils::verifyRefreshTokenValidity)
              .map(RefreshToken::getUser)
              .map(user -> {
                  UserDetailsImpl userDetails =  UserDetailsImpl.buildUserDetail(userService.findByUsername(user.getUsername()));
                  final String jwtToken = jwtUtils.generateJwtToken(userDetails);
                  return ResponseEntity.ok(new TokenRefreshResponse(jwtToken));

              })
              .orElseThrow(()-> new TokenRefreshException(providedRefreshToken, "No refresh token found"));
    }


    @GetMapping("/user/logout")
    @PermitAll
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token){
        System.out.println(token);

        return ResponseEntity.ok("SUCCESS");
    }

}
