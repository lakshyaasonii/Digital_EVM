package com.example.digital_evm.Service;

import com.example.digital_evm.Entity.Voter;
import com.example.digital_evm.Exceptions.AgeNotValidException;
import com.example.digital_evm.Exceptions.InvalidRequestException;
import com.example.digital_evm.Repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoterService {
    @Autowired
    private VoterRepository voterRepository;

    //Method to save vote entry
    @Transactional
    public void saveVote(Voter myVote){
        if(myVote.getAge() <18){
            throw new AgeNotValidException("Age must be greater than 18");
        }
        voterRepository.save(myVote);
    }

    //Method to get vote count of any party
    @Transactional(readOnly = true)
    public long partyVoteCount(String partyName){
        long count = voterRepository.countByPartyName(partyName);
        if(count == 0){
            throw new InvalidRequestException("Party not found");
        }
        return count;
    }

    //Method to get details of any voter
    @Transactional(readOnly = true)
    public Voter getVoter(long adharNumber){
        Voter v = voterRepository.findByAdharNumber(adharNumber);
        if(v == null){
            throw new InvalidRequestException("Aadhar not found !!");
        }
        return v;
    }

    //For total vote count
    @Transactional(readOnly = true)
    public long Votecount(){
        return voterRepository.count();
    }

    public List<Voter> votersDetails(){
        List<Voter> lt = new ArrayList<>();
        lt = voterRepository.findAll();
        return lt;
    }
}
