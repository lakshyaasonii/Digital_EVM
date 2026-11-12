package com.example.digital_evm.Service;

import com.example.digital_evm.Entity.Voter;
import com.example.digital_evm.Exceptions.InvalidRequestException;
import com.example.digital_evm.Repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //to encrpyt the password
        voter.setPassword(passwordEncoder.encode(voter.getPassword()));
        //to set the role of the entry
        if(voter.getRoles().isEmpty()){
            voter.setRoles(Arrays.asList("VOTER"));
        }
        voterRepository.save(voter);
    }

    @Transactional
    public void castVote(String adharNumber,String partyName){
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
    public Voter getVoter(String adharNumber){
        Voter v = voterRepository.findByAdharNumber(adharNumber);
        if(v == null){
            throw new InvalidRequestException("Voter with this adharNumber is not found !!");
        }
        return v;
    }

    //Method to resest or change the password
    @Transactional
    public void resetPassword(String adharNumber,String newPassword){
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

    //Method to get all voters details (TEMPORARY METHOD JUST FOR TESTING)
    @Transactional(readOnly = true)
    public List<Voter> getAllVoters(){
        List<Voter> list = voterRepository.findAll();
        return list;
    }

    //Method to delete any voter from db (TEMPORARY METHOD JUST FOR TESTING)
    @Transactional
    public void deleteVoter(String adharNumber){
        Voter voter = voterRepository.findByAdharNumber(adharNumber);
        if(voter == null){
            throw new InvalidRequestException("Voter with this adharNumber is not found");
        }
        voterRepository.deleteByAdharNumber(adharNumber);
    }
}
