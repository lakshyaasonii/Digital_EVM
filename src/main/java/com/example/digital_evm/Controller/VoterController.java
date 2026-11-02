package com.example.digital_evm.Controller;

import com.example.digital_evm.Entity.Voter;
import com.example.digital_evm.Service.UserDetailsServiceImpl;
import com.example.digital_evm.Service.VoterService;
import com.example.digital_evm.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/voter")
public class VoterController {
    @Autowired
    private VoterService voterService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup") //PUBLIC API
    public ResponseEntity<?> signUp(@RequestBody Voter voter){
        voterService.createVoter(voter);
        return new ResponseEntity<>("VOTER SUCESSFULLY REGISTERED",HttpStatus.CREATED);
    }

    @PostMapping("/login") //PUBLIC API
    public ResponseEntity<?> login(@RequestBody Voter voter){
        String adhrString = String.valueOf(voter.getAdharNumber());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adhrString,voter.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(adhrString);

        String token = jwtUtil.generateToken(userDetails); //JWT Token generator
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

    @PostMapping("/cast-vote/{partyName}") //AUTHENTICATED API
    public ResponseEntity<?> castVote(@PathVariable String partyName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long adharNumber = Long.parseLong(authentication.getName());
        voterService.castVote(adharNumber,partyName);
        return new ResponseEntity<>("VOTE CASTED SUCCESFULLY TO THE PARTY: " + partyName,HttpStatus.OK);
    }


    //for user to fetch their data(vote details) from cloud database
    @GetMapping("/get-vote-details") //AUTHENTICATED API
    public ResponseEntity<?> getVoterByAdhar(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//currently logged in user ki details
        long adharNumber = Long.parseLong(authentication.getName());
        return new ResponseEntity<>(voterService.getVoter(adharNumber),HttpStatus.FOUND);
    }

    //for user to reset their password
    @PutMapping("/resest-password")//AUTHENTICATED API
    public ResponseEntity<?> resetPasswordofVoter(@RequestBody Voter voter){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long adharNumber = Long.parseLong(authentication.getName());
        voterService.resetPassword(adharNumber,voter.getPassword());
        return new ResponseEntity<>("Password reset sucessfully",HttpStatus.CREATED);
    }
}
