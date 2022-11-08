package com.hcmute.myanime.controller;

import com.hcmute.myanime.auth.ApplicationUserService;
import com.hcmute.myanime.dto.AuthenticationRequestDTO;
import com.hcmute.myanime.dto.AuthenticationResponseDTO;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.dto.UserDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private JwtUtil jwtTokenUtil;


    @PostMapping(value = "/register")
    public ResponseEntity<?> saveUser(
            @RequestBody @Valid UserDTO user,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        try {
            if(applicationUserService.save(user)) {
                return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK,
                        "Create user " + user.getUsername() + " success"));
            }
        } catch (BadRequestException badRequestException) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST, "username is used"));
        }
        return ResponseEntity.badRequest().body("Register fail");
    }
    @PostMapping("/login")
    public Object authenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken
                            (authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException exception) {
            return ResponseEntity.badRequest().body("Username or password is invalid");
        }
        final UserDetails userDetails = applicationUserService
                .loadUserByUsername(authenticationRequest.getUsername());
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity
                .ok(new AuthenticationResponseDTO(jwt,
                        authenticationRequest.getUsername(),
                        role));
    }
}
