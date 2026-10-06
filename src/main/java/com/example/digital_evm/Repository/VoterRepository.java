package com.example.digital_evm.Repository;

import com.example.digital_evm.Entity.Voter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  VoterRepository extends MongoRepository<Voter, ObjectId> {
     Voter findByAdharNumber(long adharNumber);
     long countByPartyName(String partyName);
}
