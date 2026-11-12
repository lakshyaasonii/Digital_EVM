package com.example.digital_evm.Controller;

import com.example.digital_evm.Service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private VoterService voterService;

    //API to check the vote count earned by any party
    @GetMapping("/count-vote/{partyName}") //AUTHENTICATED API
    public ResponseEntity<?> countVoteByPartyName(@PathVariable String partyName){
        long earnedVote = voterService.voteCountofParty(partyName);
        long totalVote = voterService.totalVoteCount();
        double percentage = 0.0;
        if(totalVote > 0){
            percentage = ((double)earnedVote *  100)/ totalVote;
        }
        return new ResponseEntity<>("Total vote earned: " + earnedVote + " Total casted votes: " + totalVote + " Percentage: " + percentage, HttpStatus.OK);
    }

    //API to get total casted vote count
    @GetMapping("/count-vote/total") //AUTHENTICATED API
    public ResponseEntity<?> voteCount(){
        return new ResponseEntity<>(voterService.totalVoteCount(),HttpStatus.OK);
    }

    //API to get alll voters(Temporary just for complete DB return)
    @GetMapping("/allVoters")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(voterService.getAllVoters(),HttpStatus.OK);
    }

    //API to delete any specific voter (Temporary api)
    @DeleteMapping("/{adharNumber}")
    public ResponseEntity<?> deleteV(@PathVariable String adharNumber){
        voterService.deleteVoter(adharNumber);
        return new ResponseEntity<>("DELETED",HttpStatus.OK);
    }
}
