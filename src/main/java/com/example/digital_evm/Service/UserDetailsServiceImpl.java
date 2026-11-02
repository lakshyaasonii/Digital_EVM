package com.example.digital_evm.Service;

import com.example.digital_evm.Entity.Voter;
import com.example.digital_evm.Repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private VoterRepository voterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        long adharNumber = Long.parseLong(username);
        Voter voter = voterRepository.findByAdharNumber(adharNumber);

        if(voter == null){
            throw new UsernameNotFoundException("User with this adharNumber is not found in DB!!");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(String.valueOf(voter.getAdharNumber()))
                .password(voter.getPassword())
                .roles(voter.getRoles().toArray(new String[0]))
                .build();
    }
}
