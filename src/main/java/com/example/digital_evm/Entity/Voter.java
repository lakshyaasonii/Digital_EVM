package com.example.digital_evm.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "voter_entries")
public class Voter { //POJO
    @Id
    private ObjectId id;

    private String name;

    private int age;

    private String gender;

    private String partyName;

    @Indexed(unique = true)
    private long adharNumber;
}
