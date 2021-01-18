package ftn.uns.ac.rs.upp2020.controller;

import ftn.uns.ac.rs.upp2020.dto.LoginDTO;
import ftn.uns.ac.rs.upp2020.dto.TokenDTO;
import ftn.uns.ac.rs.upp2020.domain.User;
import ftn.uns.ac.rs.upp2020.dto.UserDTO;
import ftn.uns.ac.rs.upp2020.security.TokenUtils;

import ftn.uns.ac.rs.upp2020.service.AuthenticationService;
import ftn.uns.ac.rs.upp2020.service.UserService;
import org.camunda.bpm.engine.IdentityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.BadRequestException;
import java.util.stream.Collectors;


@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;
    private final TokenUtils tokenUtils;
    private final IdentityService identityService;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            AuthenticationService authenticationService,
            UserDetailsService userDetailsService,
            TokenUtils tokenUtils,
            IdentityService identityService,
            UserService userService,
            ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
        this.tokenUtils = tokenUtils;
        this.identityService = identityService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDto){
        System.out.println(">> login : username " + loginDto.getUsername() + " pass " + loginDto.getPassword());

        try{
            if(this.authenticationService.login(loginDto)){
                HttpHeaders httpHeaders = new HttpHeaders();
                TokenDTO token = new TokenDTO();
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDto.getUsername());
                String tokenValue = this.tokenUtils.generateToken(userDetails);
                token.setToken(tokenValue);
                User u = this.userService.findByUsername(loginDto.getUsername());
                token.setRole(u.getRole());

                Authentication auth = this.authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(auth);
                httpHeaders.add("X-Auth-Token", tokenValue);
                identityService.setAuthenticatedUserId(u.getUsername());
                return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);

            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException("Error occurred!");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/auth-user")
    public ResponseEntity<?> getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setGenres(user.getUserGenres()
                .stream()
                .map((ug) -> ug.getGenre().getCode())
                .collect(Collectors.toList()));

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}
