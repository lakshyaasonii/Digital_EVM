package com.example.digital_evm.Service;

import com.example.digital_evm.Entity.Voter;
import com.example.digital_evm.Exceptions.AgeNotValidException;
import com.example.digital_evm.Exceptions.InvalidRequestException;
import com.example.digital_evm.Repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Business validation class
@Service
public class VoterService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VoterRepository voterRepository;

    //Method for signup (which helps the voter to register himself)
    @Transactional
    public void createVoter(Voter voter){
        //age validation
        if(voter.getAge() <18){
            throw new AgeNotValidException("Age must be greater than 18");
        }
        //to encrpyt the password
        voter.setPassword(passwordEncoder.encode(voter.getPassword()));
        //to set the role of the entry
        if(voter.getRoles().isEmpty()){
            voter.setRoles(Arrays.asList("VOTER"));
        }
        voterRepository.save(voter);
    }

    @Transactional
    public void castVote(long adharNumber,String partyName){
        Voter voter = voterRepository.findByAdharNumber(adharNumber);
        if(voter == null){
            throw new InvalidRequestException("Voter not found!");
        }
        if(voter.getPartyName() != null){
            throw new InvalidRequestException("Vote already casted");
        }
        voter.setPartyName(partyName);
        voterRepository.save(voter);
    }

    //Method to get details of any voter
    @Transactional(readOnly = true)
    public Voter getVoter(long adharNumber){
        Voter v = voterRepository.findByAdharNumber(adharNumber);
        if(v == null){
            throw new InvalidRequestException("Voter with this adharNumber is not found !!");
        }
        return v;
    }

    //Method to resest or change the password
    @Transactional
    public void resetPassword(long adharNumber,String newPassword){
        Voter voter = voterRepository.findByAdharNumber(adharNumber);
        if(voter == null){
            throw new InvalidRequestException("Voter with this adharNumber is not found!");
        }
        voter.setPassword(passwordEncoder.encode(newPassword));
        voterRepository.save(voter);
    }

    //Method for ADMIN to count vote of any party name
    @Transactional(readOnly = true)
    public long voteCountofParty(String partyName){
        long count = voterRepository.countByPartyName(partyName);
        return count;
    }

    //Method to get total casted vote count
    @Transactional(readOnly = true)
    public long totalVoteCount(){
        long count = voterRepository.countByPartyNameIsNotNull();
        return count;
    }
}
