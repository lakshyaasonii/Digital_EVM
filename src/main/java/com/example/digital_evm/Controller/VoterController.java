package com.example.digital_evm.Controller;

import com.example.digital_evm.Entity.Voter;
import com.example.digital_evm.Service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voter")
public class VoterController {
    @Autowired
    private VoterService voterService;

    @PostMapping
    public ResponseEntity<?> saveEntry(@RequestBody Voter myVote){
        voterService.saveVote(myVote);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/party/{partyName}")
    public ResponseEntity<?> getPartyVote(@PathVariable String partyName){
        return new ResponseEntity<>(voterService.partyVoteCount(partyName),HttpStatus.OK);
    }

    @GetMapping("/adhar/{adharNumber}")
    public ResponseEntity<?> getVoterByAdhar(@PathVariable long adharNumber){
        return new ResponseEntity<>(voterService.getVoter(adharNumber),HttpStatus.OK);
    }

    @GetMapping("/totalVote")
    public ResponseEntity<?> totalVote(){
        return new ResponseEntity<>(voterService.Votecount(),HttpStatus.OK);
    }

    @GetMapping("/allVote")
    public ResponseEntity<?> allVoters(){
        return new ResponseEntity<>(voterService.votersDetails(),HttpStatus.OK);
    }
}
